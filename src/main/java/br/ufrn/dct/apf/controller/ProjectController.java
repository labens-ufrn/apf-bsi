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

import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.service.ProjectService;

@Controller
public class ProjectController {

        @Autowired
        private ProjectService service;

        @GetMapping("/project")
        public ModelAndView findAll() {

            ModelAndView mv = new ModelAndView("project/list");
            mv.addObject("projects", service.findAll());

            return mv;
        }

        @GetMapping("/project/add")
        public ModelAndView add(Project project) {

            ModelAndView mv = new ModelAndView("project/add");
            mv.addObject("project", project);

            return mv;
        }

        @GetMapping("/project/edit/{id}")
        public ModelAndView edit(@PathVariable("id") Long id) {

            return add(service.findOne(id));
        }

        @GetMapping("/project/delete/{id}")
        public ModelAndView delete(@PathVariable("id") Long id) {

            service.delete(id);

            return findAll();
        }

        @PostMapping("/project/save")
        public ModelAndView save(@Valid Project project, BindingResult result) {

            if(result.hasErrors()) {
                return add(project);
            }

            service.save(project);

            return findAll();
        }
}
