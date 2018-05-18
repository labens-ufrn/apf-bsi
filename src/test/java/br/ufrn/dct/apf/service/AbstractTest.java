package br.ufrn.dct.apf.service;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import br.ufrn.dct.apf.model.User;

public class AbstractTest extends AbstractTestNGSpringContextTests {
    
    protected User createUser(String name, String lastName) {
        User user = new User();

        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(name+lastName+"@gmail.com");
        user.setPassword("12345");
        user.setActive(1);

        return user;
    }

}
