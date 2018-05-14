package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.model.Member;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.repository.MemberRepository;
import br.ufrn.dct.apf.repository.ProjectRepository;
import br.ufrn.dct.apf.repository.RoleRepository;

@Service
public class ProjectService {

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
    
    public Project save(Project project, User owner) {

        addMember(project, owner);


        project.setActive(1);
        
        return repository.saveAndFlush(project);
    }
    
    private void addMember(Project project, User owner) {
        Member m1 = new Member();
        m1.setProject(project);
        m1.setUser(owner);
        m1.setCreatedOn(project.getCreatedOn());
        
        if (project != null && project.getId() == null) {
            Role projectManager = roleRepository.findByRoleName(Role.PROJECT_MANAGER_ROLE);
            owner.setNewRole(projectManager);
            project.setOwner(m1);
        } else if (project != null && project.getId() != null && !containsMember(m1)) {
            Role projectMember = roleRepository.findByRoleName(Role.USER_ROLE);
            owner.setNewRole(projectMember);
        }
    }
    
    private boolean containsMember(Member m) {
        List<Member> team = memberRepository.findByProjectIdAndUserId(m.getProject().getId(), m.getUser().getId());
        return team.contains(m);
    }

    public Project save(Project project) {
        project.setActive(1);
        return repository.saveAndFlush(project);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
