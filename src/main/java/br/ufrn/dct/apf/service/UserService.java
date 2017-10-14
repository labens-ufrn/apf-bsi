package br.ufrn.dct.apf.service;

import br.ufrn.dct.apf.model.User;

public interface UserService {

    public User findUserByEmail(String email);

    public void saveUser(User user);

}
