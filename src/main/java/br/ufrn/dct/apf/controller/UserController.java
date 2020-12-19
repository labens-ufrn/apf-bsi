package br.ufrn.dct.apf.controller;

import br.ufrn.dct.apf.dto.UserDTO;
import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.repository.RoleRepository;
import br.ufrn.dct.apf.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController extends AbstractController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    public UserController(@Qualifier("roleRepository") RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping("/user")
    public ModelAndView profile(ModelAndView mv) {
        mv.setViewName("user/profile");

        UserDTO current = getCurrentUser();
        setUserAuth(mv);

        mv.addObject("user", userService.findOne(current.getId()));
        return mv;
    }

    @GetMapping("/user/edit/{id}")
    public ModelAndView editProfile(@PathVariable("id") Long id) {
        return update(userService.findOne(id));
    }

    @GetMapping("/user/update")
    public ModelAndView update(UserDTO user) {
        ModelAndView mv = new ModelAndView("user/edit");

        mv.addObject("user", user);

        return mv;
    }

    @PostMapping("/user/save")
    public ModelAndView saveProfile(@Valid UserDTO user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        checkEmailExists(user, result);
        if (result.hasErrors()) {
            result.reject("erro!", result.getAllErrors().toArray(), "Erro ao editar usuário!");
            modelAndView.addObject("errorMessage", "User has not been updated.");
            modelAndView.setViewName("user/edit");
            return modelAndView;
        } else {
            userService.edit(user);

            loadUserAuth(user.getEmail(), user.getPassword());

            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new UserDTO());
        }

        return profile(modelAndView);
    }

    @GetMapping("/admin/user")
    public ModelAndView findAll() {

        ModelAndView mv = new ModelAndView("admin/user/list");

        setUserAuth(mv);

        mv.addObject("users", userService.findAll());

        return mv;
    }

    @GetMapping("/admin/user/add")
    public ModelAndView add(UserDTO user) {
        List<Role> regras = roleRepository.findAll();

        ModelAndView mv = new ModelAndView("admin/user/add");
        mv.addObject("regras", regras);
        mv.addObject("user", user);

        return mv;
    }

    @GetMapping("/admin/user/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        return add(userService.findOne(id));
    }

    @GetMapping("/admin/user/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return findAll();
    }

    @PostMapping("/admin/user/save")
    public ModelAndView save(@Valid UserDTO user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        checkEmailExists(user, result);
        if (result.hasErrors()) {
            result.reject("erro!", result.getAllErrors().toArray(), "Erro ao salvar usuário");
            findAll();
        } else {
            userService.save(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new UserDTO());
        }

        return findAll();
    }

    private void checkEmailExists(UserDTO user, BindingResult result) {
        UserDTO userExists = userService.findUserByEmail(user.getEmail());

        if (userExists != null && !userExists.getId().equals(user.getId())) {
            result.rejectValue("email", "error.user", "There is already a user registered with the email provided");
        }
    }
}
