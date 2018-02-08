package br.ufrn.dct.apf.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.service.UserService;

@Controller
public class LoginController extends AbstractController {

    @Autowired
    private UserService userService;

    private User overridenCurrentUser;
    
    private final String LOGIN_VIEW = "login";
    
    private final String REGISTRATION_VIEW = "registration";

    @RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LOGIN_VIEW);
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName(REGISTRATION_VIEW);
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user",
                    "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(REGISTRATION_VIEW);
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName(REGISTRATION_VIEW);

        }
        return modelAndView;
    }

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public ModelAndView homeAdmin() {
        ModelAndView modelAndView = new ModelAndView();

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("authorities", getRoles(user));
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("authorities", getRoles(user));
        modelAndView.addObject("userMessage", "Content Available for Users");
        modelAndView.setViewName("home");
        return modelAndView;
    }

    public String getUsername() {
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

    public User getCurrentUser() {
        if (overridenCurrentUser != null) {
            return overridenCurrentUser;
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        User user = userService.findUserByEmail(auth.getName());

        return user;
    }
}
