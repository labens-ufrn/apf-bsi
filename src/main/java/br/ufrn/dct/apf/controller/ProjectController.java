package br.ufrn.dct.apf.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.service.BusinessRuleException;
import br.ufrn.dct.apf.service.ProjectService;

@Controller
public class ProjectController extends AbstractController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectService.class.getName());

    @Autowired
    private ProjectService service;

    @GetMapping("/project")
    public ModelAndView findAll() {

        ModelAndView mv = new ModelAndView("project/list");
        setUserAuth(mv);

        User current = getCurrentUser();

        mv.addObject("projects", service.findByUserId(current.getId()));

        return mv;
    }

    @GetMapping("/project/add")
    public ModelAndView add(Project project) {

        ModelAndView mv = new ModelAndView("project/add");
        mv.addObject("project", project);

        return mv;
    }

    @GetMapping("/project/view/{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("project/view");
        setUserAuth(mv);

        Project project = service.findOne(id);
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
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            return add(project);
        }

        User user = getCurrentUser();
        try {
            service.save(project, user);
        } catch (BusinessRuleException e) {
            LOGGER.error("error.project.controller.save", e);
            modelAndView.addObject("errorMessage", "User has been registered successfully");
        }

        return findAll();
    }

    // http://localhost:8080/suggestion?searchstr=car

    /**
     * the rest controller which is requested by the autocomplete input field
     * instead of the countries here could also be made a DB request
     */
    @RequestMapping(value = "/project/suggestion", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ProjectSuggestionWrapper autocompleteSuggestions(@RequestParam("searchstr") String searchstr) {
        LOGGER.log(Level.INFO, "searchstr = "+ searchstr);

        List<ProjectSuggestion> suggestions = new ArrayList<>();
        User current = getCurrentUser();
        List<Project> projects = service.findByName(current.getId(), searchstr);

        for (Project project : projects) {
            ProjectSuggestion ps = new ProjectSuggestion(project.getName());
            ps.setData(project.getId().toString());
            suggestions.add(ps);
        }

        // truncate the list to the first n, max 20 elements
        int n = suggestions.size() > 20 ? 20 : suggestions.size();
        List<ProjectSuggestion> sulb = new ArrayList<>(suggestions.subList(0, n));

        ProjectSuggestionWrapper sw = new ProjectSuggestionWrapper();
        sw.setSuggestions(sulb);
        return sw;
    }
}
