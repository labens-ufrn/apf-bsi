package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;

    public List<Project> findAll() {
        return repository.findAll();
    }
    
    public List<Project> findByUserId(Long user) {
        return repository.findByUserId(user);
    }

    public Project findOne(Long id) {
        return repository.findOne(id);
    }

    public Project save(Project project) {
        return repository.saveAndFlush(project);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
