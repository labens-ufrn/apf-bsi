package br.ufrn.dct.apf.controller;

import java.util.Set;

import br.ufrn.dct.apf.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.service.UserService;

public abstract class AbstractController {

    private UserDTO overridenCurrentUser;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authManager;

    protected void setUserAuth(ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.findUserByEmail(auth.getName());

        modelAndView.addObject("userName", user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("authorities", getRoles(user));
    }

    protected void loadUserAuth(String email, String password) {
        UsernamePasswordAuthenticationToken authReq =
            new UsernamePasswordAuthenticationToken(email, password);
	    Authentication auth = authManager.authenticate(authReq);
	    SecurityContext sc = SecurityContextHolder.getContext();
	    sc.setAuthentication(auth);
    }

    protected String getUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null)
            return null;

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    protected UserDTO getCurrentUser() {
        if (overridenCurrentUser != null) {
            return overridenCurrentUser;
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        return userService.findUserByEmail(auth.getName());
    }

    protected String getRoles(UserDTO user) {
        Set<Role> roles = user.getRoles();

        StringBuilder bld = new StringBuilder();
        bld.append("[");
        int size = roles.size();
        int i = 1;
        for (Role role : roles) {
            bld.append(role.getRoleName());
            if (i > 0 && i < size) {
                bld.append(", ");
                i += 1;
            }
        }
        bld.append("]");
        return bld.toString();
    }
}
