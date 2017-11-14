package br.ufrn.dct.apf.service;

import java.util.List;

import br.ufrn.dct.apf.model.User;

public interface UserService {

    public User findUserByEmail(String email);

    public void saveUser(User user);

    public List<User> findAll();

    public User findOne(Long id);

}
