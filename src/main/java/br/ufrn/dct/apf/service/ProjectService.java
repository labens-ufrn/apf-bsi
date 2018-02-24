package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.model.Member;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.repository.ProjectRepository;
import br.ufrn.dct.apf.repository.RoleRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;
    
    @Autowired
    private RoleRepository roleRepository;

    public List<Project> findAll() {
        return repository.findAll();
    }
    
    public List<Project> findByUserId(Long user) {
        return repository.findByUserId(user);
    }

    public Project findOne(Long id) {
        return repository.findOne(id);
    }
    
    public Project save(Project project, User owner) {
        
        Member m1 = new Member();
        m1.setProject(project);
        m1.setUser(owner);
        m1.setCreatedOn(project.getCreated());
        
        //TODO Colocar ou não uma nova regra para User
        Role projectManager = roleRepository.findByRole(Role.PROJECT_MANAGER_ROLE);
        //owner.setNewRole(projectManager);
        
        project.setOwner(m1);
        
        return repository.saveAndFlush(project);
    }

    public Project save(Project project) {
        return repository.saveAndFlush(project);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
