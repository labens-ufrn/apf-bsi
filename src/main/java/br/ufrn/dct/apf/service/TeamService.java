package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.model.Team;
import br.ufrn.dct.apf.repository.TeamRepository;

@Service
public class TeamService {

    @Autowired
    private TeamRepository repository;

    public List<Team> findAll() {
        return repository.findAll();
    }

    public Team findOne(Long id) {
        return repository.findOne(id);
    }

    public Team save(Team team) {
        return repository.saveAndFlush(team);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
