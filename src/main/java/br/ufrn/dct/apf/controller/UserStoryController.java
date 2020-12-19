package br.ufrn.dct.apf.controller;

import java.util.List;

import javax.validation.Valid;

import br.ufrn.dct.apf.dto.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private static final Logger logger = LogManager.getLogger(UserStoryController.class);

    @Autowired
    private UserStoryService service;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/us/add/{projectId}")
    public ModelAndView add(UserStory us, @PathVariable("projectId") Long id) {

        ModelAndView mv = new ModelAndView("us/add");
        Project project = projectService.findOne(id);
        us.setProject(project);
        mv.addObject("userstory", us);

        return mv;
    }

    @GetMapping("/us/add")
    public ModelAndView add(UserStory us) {

        ModelAndView mv = new ModelAndView("us/add");
        mv.addObject("userstory", us);

        return mv;
    }

    @GetMapping("/us/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        return add(service.findOne(id));
    }

    @GetMapping("/us/list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("us/list");
        UserDTO current = getCurrentUser();

        List<Project> projects = projectService.findByUserId(current.getId());

        mv.addObject("projects", projects);
        mv.addObject("message", "Baeldung");

        return mv;
    }

    @PostMapping("/us/save")
    public ModelAndView save(@Valid UserStory us, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            logger.error("error.us.controller.save");
            modelAndView.addObject("errorMessage", "UserStory com erro!");
            return add(us);
        }

        UserDTO current = getCurrentUser();

        Project project;

        if (us.getProject() != null && us.getProject().getId() != null) {
            project = projectService.findOne(us.getProject().getId());
        } else {
            List<Project> projects = projectService.findByName(current.getId(), us.getProject().getName());
            project = projects.get(0);
        }

        us.setProject(project);

        service.save(us);

        UserStory newUS = new UserStory();
        newUS.setProject(project);

        return add(newUS);
    }
}
