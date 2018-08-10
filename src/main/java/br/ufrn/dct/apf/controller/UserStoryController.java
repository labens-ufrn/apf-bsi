package br.ufrn.dct.apf.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.service.ProjectService;
import br.ufrn.dct.apf.service.UserStoryService;

@Controller
public class UserStoryController extends AbstractController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UserStoryController.class.getName());

    @Autowired
    private UserStoryService service;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/us/add")
    public ModelAndView add(UserStory us) {

        ModelAndView mv = new ModelAndView("us/add");
        mv.addObject("userstory", us);

        return mv;
    }

    @PostMapping("/us/save")
    public ModelAndView save(@Valid UserStory us, BindingResult result) {

        if (result.hasErrors()) {
            LOGGER.error("error.us.controller.save");
            return add(us);
        }

        User current = getCurrentUser();

        List<Project> projects = projectService.findByName(current.getId(), us.getProject().getName());

        us.setProject(projects.get(0));

        service.save(us);

        UserStory newUS = new UserStory();
        newUS.setProject(projects.get(0));

        return add(newUS);
    }
}
