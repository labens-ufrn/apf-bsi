package br.ufrn.dct.apf.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.dct.apf.count.EstimativeCount;
import br.ufrn.dct.apf.count.IndicativeCount;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.service.ProjectService;
import br.ufrn.dct.apf.service.UserService;

@Controller
public class LoginController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    private static final String LOGIN_VIEW = "login";

    private static final String REGISTRATION_VIEW = "registration";

    @GetMapping(path = { "/", "/login" })
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LOGIN_VIEW);
        return modelAndView;
    }

    @GetMapping(path = "/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName(REGISTRATION_VIEW);
        return modelAndView;
    }

    @PostMapping(path = "/registration")
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
            userService.save(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName(REGISTRATION_VIEW);

        }
        return modelAndView;
    }

    @GetMapping(path = "/admin/home")
    public ModelAndView homeAdmin() {
        ModelAndView modelAndView = new ModelAndView();

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("authorities", getRoles(user));
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @GetMapping(path = "/home")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("home");
        setUserAuth(mv);
        User user = getCurrentUser();

        mv.addObject("projects", projectService.findByUserId(user.getId()));

        return mv;
    }

    @GetMapping(path = "/dashboard")
    public ModelAndView dashboard() {
        ModelAndView mv = new ModelAndView("dashboard");
        setUserAuth(mv);

        mv.addObject("projects", projectService.findByIsPrivateFalse());
        mv.addObject("icounter", new IndicativeCount());
        mv.addObject("ecounter", new EstimativeCount());

        return mv;
    }

    // for 403 access denied page
    @GetMapping(path = "/access-denied")
    public ModelAndView accesssDenied() {

        ModelAndView model = new ModelAndView();

        User user = getCurrentUser();

        if (user != null) {
            model.addObject("msg", "Hi " + user.getFirstName() + ", you do not have permission to access this page!");
        } else {
            model.addObject("msg", "You do not have permission to access this page!");
        }

        model.setViewName("access-denied");
        return model;
    }
}
