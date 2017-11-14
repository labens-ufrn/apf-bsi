package br.ufrn.dct.apf.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.service.UserService;

public class AbstractController {

    @Autowired
    private UserService userService;

    protected void setUserAuth(ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("authorities", getRoles(user));
    }

    private String getRoles(User user) {
        Set<Role> roles = user.getRoles();
        String str = "[";
        int size = roles.size();
        int i = 1;
        for (Role role : roles) {
            str += role.getRole();
            if (i > 1 && i < size) {
                str += ", ";
            }
        }
        return str + "]";
    }

}