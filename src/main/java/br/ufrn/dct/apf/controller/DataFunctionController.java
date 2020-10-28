package br.ufrn.dct.apf.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import br.ufrn.dct.apf.model.DataFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import br.ufrn.dct.apf.dto.DataFunctionDTO;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.service.DataFunctionService;
import br.ufrn.dct.apf.service.ProjectService;

@Controller
public class DataFunctionController extends AbstractController {

    /**
     * Logger.
     */
    private static final Logger logger = LogManager.getLogger(DataFunctionController.class);

    @Autowired
    private DataFunctionService dataFunctionService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/df/add/{projectId}")
    public ModelAndView add(DataFunctionDTO df, @PathVariable("projectId") Long id) {

        ModelAndView mv = new ModelAndView("df/add");
        Project project = projectService.findOne(id);
        df.setProject(project);
        Set<UserStory> userStories = project.getUserStories();
        UserStory userStory = new UserStory();

        mv.addObject("project", project);
        mv.addObject("userStories", userStories);
        mv.addObject("userStory", userStory);

        mv.addObject("dfDTO", df);

        return mv;
    }

    @GetMapping("/df/add")
    public ModelAndView add(DataFunctionDTO df) {
        ModelAndView mv = new ModelAndView("df/add");
        setUserAuth(mv);

        Project project = new Project();
        Set<UserStory> userStories = new HashSet<>();
        UserStory userStory = new UserStory();
        if (df != null && df.getProject() != null) {
            project = projectService.findOne(df.getProject().getId());
            df.setProject(project);
            userStories = project.getUserStories();
        }

        mv.addObject("project", project);
        mv.addObject("userStories", userStories);
        mv.addObject("userStory", userStory);

        mv.addObject("dfDTO", df);

        return mv;
    }

//    @GetMapping("/df/list")
//    public ModelAndView list() {
//        ModelAndView mv = new ModelAndView("df/list");
//
//        User current = getCurrentUser();
//
//        List<Project> projects = projectService.findByUserId(current.getId());
//
//        mv.addObject("projects", projects);
//
//        return mv;
//    }

    @PostMapping("/df/save")
    public ModelAndView save(@Valid DataFunctionDTO dfDto, BindingResult result) {

        if (result.hasErrors()) {
            logger.error("error.df.controller.save");
            return add(dfDto);
        }

        User current = getCurrentUser();

        List<Project> projects = projectService.findByName(current.getId(), dfDto.getProject().getName());
        Project p = null;
        if (!projects.isEmpty()) {
            p = projects.get(0);
            dfDto.setProject(p);
        }

        dataFunctionService.save(dfDto);

        DataFunctionDTO newDF = new DataFunctionDTO();
        newDF.setProject(p);

        return add(newDF);
    }

    @GetMapping("/df/details/{dataFunctionId}")
    public ModelAndView details(@PathVariable("dataFunctionId") Long id) {
        ModelAndView mv = new ModelAndView("df/details");

        DataFunction df = dataFunctionService.findOne(id);

        mv.addObject("df", df);

        return mv;
    }

    // Consultar se precisa ser por projeto ou time a busca
    @GetMapping("/df/list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("df/list");

        List<DataFunction> dfs = dataFunctionService.findAll();

        mv.addObject("dfs", dfs);

        return mv;
    }

}
