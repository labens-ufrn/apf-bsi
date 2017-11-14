package br.ufrn.dct.apf.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.service.UserService;

@Controller
public class UserController extends AbstractController {

        @Autowired
        private UserService service;

        @GetMapping("/admin/user")
        public ModelAndView findAll() {

            ModelAndView mv = new ModelAndView("admin/user/list");

            setUserAuth(mv);

            mv.addObject("users", service.findAll());

            return mv;
        }

        @GetMapping("/admin/user/add")
        public ModelAndView add(User user) {

            ModelAndView mv = new ModelAndView("admin/user/add");
            mv.addObject("user", user);

            return mv;
        }

        @GetMapping("/admin/user/edit/{id}")
        public ModelAndView edit(@PathVariable("id") Long id) {

            return add(service.findOne(id));
        }

        @GetMapping("/admin/user/delete/{id}")
        public ModelAndView delete(@PathVariable("id") Long id) {

            //service.delete(id);

            return findAll();
        }

        @PostMapping("/admin/user/save")
        public ModelAndView save(@Valid User user, BindingResult result) {

            if(result.hasErrors()) {
                return add(user);
            }

            //service.save(user);

            return findAll();
        }
}
