package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.repository.UserStoryRepository;

@Service
public class UserStoryService extends AbstractService {

    @Autowired
    private UserStoryRepository repository;

    public List<UserStory> findAll() {
        return repository.findAll();
    }

    public UserStory findOne(Long id) {
        return repository.findOne(id);
    }

    public UserStory save(UserStory userStory) {
        return repository.saveAndFlush(userStory);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
