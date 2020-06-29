package br.ufrn.dct.apf.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.model.Attribution;
import br.ufrn.dct.apf.model.Member;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.repository.AttributionRepository;
import br.ufrn.dct.apf.repository.MemberRepository;
import br.ufrn.dct.apf.repository.ProjectRepository;

@Service
public class ProjectService extends AbstractService {

    /**
     * Logger.
     */
    private static final Logger logger = LogManager.getLogger(ProjectService.class);

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private AttributionRepository attribRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<Project> findAll() {
        return repository.findAll();
    }

    public List<Project> findByUserId(Long user) {
        return repository.findByUserId(user);
    }

    public List<Project> findByName(Long user, String name) {
        return repository.findByTeamUserIdAndNameContaining(user, name);
    }

    public Project findOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Project> findByIsPrivateFalse() {
        return repository.findByIsPrivateFalse();
    }

    public Project addMember(Project project, User newMember, User owner) throws BusinessRuleException {
        addMember(project, newMember);
        return save(project, owner);
    }

    public Project save(Project project, User owner) throws BusinessRuleException {
        logger.info("save " + project);
        checkProjectNull(project);
        checkMemberNull(owner);

        if (project.getId() == null) {
            addMember(project, owner);
        } else if (project.getId() != null) {
            checkOwnerMember(project, owner);
        }

        project.setActive(ACTIVE);

        return repository.saveAndFlush(project);
    }

    private void addMember(Project project, User user) {
        Attribution projectManager = attribRepository.findByName(Attribution.PROJECT_MANAGER);
        Member manager = createMember(project, user, projectManager);
        project.addMember(manager);
    }

    private void checkOwnerMember(Project project, User user) throws BusinessRuleException {
        logger.info("checkMember " + project + " - " + user);
        if (!isOwnerMember(project, user)) {
            throw new BusinessRuleException("error.project.service.member.not.exists");
        }
    }

    private boolean isOwnerMember(Project project, User user) {
        if (project.getId() != null && user != null && user.getId() != null) {
            List<Member> team = memberRepository.findByProjectIdAndUserId(project.getId(), user.getId());
            return team.size() == 1;
        }
        return false;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long id) {
        logger.info("delete.id = " + id);
        repository.deleteById(id);
    }

    private void checkProjectNull(Object obj) throws BusinessRuleException {
        if (checkNull(obj)) {
            throw new BusinessRuleException("error.project.service.project.is.null");
        }
    }

    private void checkMemberNull(Object obj) throws BusinessRuleException {
        if (checkNull(obj)) {
            throw new BusinessRuleException("error.project.service.member.is.null");
        }
    }
}
