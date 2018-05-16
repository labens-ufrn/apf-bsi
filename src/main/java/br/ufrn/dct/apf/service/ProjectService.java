package br.ufrn.dct.apf.service;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.model.Member;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.repository.MemberRepository;
import br.ufrn.dct.apf.repository.ProjectRepository;
import br.ufrn.dct.apf.repository.RoleRepository;

@Service
public class ProjectService extends AbstractService {
    
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectService.class.getName());

    @Autowired
    private ProjectRepository repository;
    
    @Autowired
    private RoleRepository roleRepository;
    
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
        return repository.findOne(id);
    }
    
    public Project save(Project project) throws BusinessRuleException {
        checkMember(project, null);
        project.setActive(ACTIVE);
        return repository.saveAndFlush(project);
    }
    
    public Project addMember(Project project, User newMember, User owner) throws BusinessRuleException {
        addMember(project, newMember);
        return save(project, owner);
    }
    
    public Project save(Project project, User owner) throws BusinessRuleException {
        LOGGER.log(Level.INFO, project);
        checkProjectNull(project);
        checkMemberNull(owner);

        if (project.getId() == null) {
            addMember(project, owner);
        } else if (project.getId() != null) {
            checkMember(project, owner);
        }

        project.setActive(ACTIVE);
        
        return repository.saveAndFlush(project);
    }
    
    private void addMember(Project project, User user) throws BusinessRuleException {
        Role projectManager = roleRepository.findByRoleName(Role.PROJECT_MANAGER_ROLE);
        user.setNewRole(projectManager);
        Member manager = createMember(project, user);
        project.setOwner(manager);
    }
    
    private void checkMember(Project project, User user) throws BusinessRuleException {
        LOGGER.log(Level.INFO, "ProjectService.checkMember");
        if (!containsMemberInTeam(project, user)) {
            throw new BusinessRuleException("error.project.service.member.not.exists");
        }
    }
    
    private boolean containsMemberInTeam(Project project, User user) {
        if (project.getId() != null && user != null && user.getId() != null) {
            List<Member> team = memberRepository.findByProjectIdAndUserId(project.getId(), user.getId());
            return team.size() == 1;
        } else if (project.getTeam() != null) {
            return project.getTeam().size() >= 1;
        }
        return false;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "ProjectService.delete.id = "+ id);
        repository.delete(id);
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
