package br.ufrn.dct.apf.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.repository.RoleRepository;
import br.ufrn.dct.apf.service.UserService;

@Controller
public class UserController extends AbstractController {

        @Autowired
        private UserService service;

        @Autowired
        private RoleRepository roleRepository;

        @GetMapping("/user")
        public ModelAndView profile() {

            ModelAndView mv = new ModelAndView("user/profile");

            User current = getCurrentUser();

            setUserAuth(mv);

            mv.addObject("user", service.findOne(current.getId()));

            return mv;
        }

        @GetMapping("/user/edit/{id}")
        public ModelAndView editProfile(@PathVariable("id") Long id) {

            return update(service.findOne(id));
        }

        @GetMapping("/user/update")
        public ModelAndView update(User user) {
            ModelAndView mv = new ModelAndView("user/edit");

            mv.addObject("user", user);

            return mv;
        }

        @PostMapping("/user/save")
        public ModelAndView saveProfile(@Valid User user, BindingResult result) {
            ModelAndView modelAndView = new ModelAndView();

            service.edit(user);

            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());

            return profile();
        }

        @GetMapping("/admin/user")
        public ModelAndView findAll() {

            ModelAndView mv = new ModelAndView("admin/user/list");

            setUserAuth(mv);

            mv.addObject("users", service.findAll());

            return mv;
        }

        @GetMapping("/admin/user/add")
        public ModelAndView add(User user) {
            List<Role> regras = roleRepository.findAll();

            ModelAndView mv = new ModelAndView("admin/user/add");
            mv.addObject("regras", regras);
            mv.addObject("user", user);

            return mv;
        }

        @GetMapping("/admin/user/edit/{id}")
        public ModelAndView edit(@PathVariable("id") Long id) {
            return add(service.findOne(id));
        }

        @GetMapping("/admin/user/delete/{id}")
        public ModelAndView delete(@PathVariable("id") Long id) {
            service.delete(id);
            return findAll();
        }

        @PostMapping("/admin/user/save")
        public ModelAndView save(@Valid User user, BindingResult result) {
            ModelAndView modelAndView = new ModelAndView();
            User userExists = service.findUserByEmail(user.getEmail());
            if (userExists != null) {
                result.rejectValue("email", "error.user",
                        "There is already a user registered with the email provided");
            }
            if (result.hasErrors()) {
                result.reject("erro!", result.getAllErrors().toArray(), "Erro ao salvar usuário");
                findAll();
            } else {
                service.save(user);
                modelAndView.addObject("successMessage", "User has been registered successfully");
                modelAndView.addObject("user", new User());
            }

            return findAll();
        }
}
