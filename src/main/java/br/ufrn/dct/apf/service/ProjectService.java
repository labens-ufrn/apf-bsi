package br.ufrn.dct.apf.service;

import java.util.List;

import br.ufrn.dct.apf.dto.UserDTO;
import br.ufrn.dct.apf.model.*;
import br.ufrn.dct.apf.utils.BusinessExceptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.repository.AttributionRepository;
import br.ufrn.dct.apf.repository.ProjectRepository;

import static java.util.Objects.isNull;

@Service
public class ProjectService extends AbstractService {

    private static final Logger logger = LogManager.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;

    private final AttributionRepository attributionRepository;

    public ProjectService(@Qualifier("projectRepository") ProjectRepository projectRepository, @Qualifier("attributionRepository") AttributionRepository attributionRepository) {
        this.projectRepository = projectRepository;
        this.attributionRepository = attributionRepository;
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public List<Project> findByUserId(Long user) {
        return projectRepository.findByUserId(user);
    }

    public List<Project> findByName(Long user, String name) {
        return projectRepository.findByTeamUserIdAndNameContaining(user, name);
    }

    public Project findOne(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public List<Project> findByIsPrivateFalse() {
        return projectRepository.findByIsPrivateFalse();
    }

    public Project addMember(Project project, UserDTO newMember, UserDTO owner) throws BusinessRuleException {
        addMember(project, newMember, Attribution.PROJECT_MEMBER);

        return save(project, owner);
    }

    public Project save(Project project, UserDTO owner) throws BusinessRuleException {
        logger.info(() -> "projectService.save: " + project + ", owner" + owner);
        if (isNull(project)) {
            throw BusinessExceptions.PROJECT_IS_NULL;
        }
        if (isNull(owner)) {
            throw BusinessExceptions.MEMBER_IS_NULL;
        }

        if (isNull(project.getId())) {
            addMember(project, owner, Attribution.PROJECT_MANAGER);
        } else {
            checkIsOwnerMember(project, owner);
        }

        project.setActive(ACTIVE);

        return projectRepository.saveAndFlush(project);
    }

    private void addMember(Project project, UserDTO user, String attr) {
        Attribution attribution = attributionRepository.findByName(attr);
        Member manager = Member.factoryFromProjectAndUser(project, user.convertToEntity(), attribution);

        project.addMember(manager);
    }

    private void checkIsOwnerMember(Project project, UserDTO user) throws BusinessRuleException {
        if (!project.isMemberOfProject(user.convertToEntity())) {
            throw BusinessExceptions.MEMBER_NOT_EXISTS;
        }

        if (!hasAttribution(project, user.convertToEntity(), Attribution.PROJECT_MANAGER)) {
            throw BusinessExceptions.WITHOUT_ATTRIBUTION;
        }
    }

    private boolean hasAttribution(Project project, User user, String attribution) {
        boolean hasRule = false;
        Member memberOfUser = project.getMemberFromUser(user);

        if (!isNull(project.getId()) && !isNull(user.getId())) {
            Attribution attributionToCheck = attributionRepository.findByName(attribution);

            hasRule = memberOfUser != null && memberOfUser.getAttribution().equals(attributionToCheck);
        }

        return hasRule;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long id) {
        logger.info("delete.id = " + id);
        projectRepository.deleteById(id);
    }

}
