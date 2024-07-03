package br.ufrn.dct.apf.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.dct.apf.dto.TransactionFunctionDTO;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.service.TransactionFunctionService;
import br.ufrn.dct.apf.service.ProjectService;

@Controller
public class TransactionFunctionController extends AbstractController {

    /**
     * Logger.
     */
    private static final Logger logger = LogManager.getLogger(TransactionFunctionController.class);

    @Autowired
    private TransactionFunctionService service;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/tf/add/{projectId}")
    public ModelAndView add(TransactionFunctionDTO tf, @PathVariable("projectId") Long id) {
        ModelAndView mv = new ModelAndView("tf/add");

        Project project = projectService.findOne(id);
        tf.setProject(project);
        Set<UserStory> userStories = project.getUserStories();
        UserStory userStory = new UserStory();

        mv.addObject("project", project);
        mv.addObject("userStories", userStories);
        mv.addObject("userStory", userStory);

        mv.addObject("tfDTO", tf);

        return mv;
    }

    @GetMapping("/tf/add")
    public ModelAndView add(TransactionFunctionDTO tf) {
        ModelAndView mv = new ModelAndView("tf/add");
        setUserAuth(mv);

        Project project = new Project();
        Set<UserStory> userStories = new HashSet<>();
        UserStory userStory = new UserStory();
        if (tf != null && tf.getProject() != null) {
            project = projectService.findOne(tf.getProject().getId());
            tf.setProject(project);
            userStories = project.getUserStories();
        }

        mv.addObject("project", project);
        mv.addObject("userStories", userStories);
        mv.addObject("userStory", userStory);

        mv.addObject("tfDTO", tf);

        return mv;
    }

    @GetMapping("/tf/list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("tf/list");

        User current = getCurrentUser();

        List<Project> projects = projectService.findByUserId(current.getId());

        mv.addObject("projects", projects);

        return mv;
    }

    @PostMapping("/tf/save")
    public ModelAndView save(@Valid TransactionFunctionDTO tfDto, BindingResult result) {

        if (result.hasErrors()) {
            logger.error("error.df.controller.save");
            return add(tfDto);
        }

        User current = getCurrentUser();

        List<Project> projects = projectService.findByName(current.getId(), tfDto.getProject().getName());
        Project p = null;
        if (!projects.isEmpty()) {
            p = projects.get(0);
            tfDto.setProject(p);
        }

        service.save(tfDto);

        TransactionFunctionDTO newTF = new TransactionFunctionDTO();
        newTF.setProject(p);

        return add(newTF);
    }
}
