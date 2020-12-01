package br.ufrn.dct.apf.controller;

import br.ufrn.dct.apf.dto.UserDTO;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.service.BusinessRuleException;
import br.ufrn.dct.apf.service.ProjectService;
import br.ufrn.dct.apf.utils.BusinessExceptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;

@Controller
public class ProjectController extends AbstractController {

    /**
     * Logger.
     */
    private static final Logger logger = LogManager.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @GetMapping("/project")
    public ModelAndView findAll() {

        ModelAndView mv = new ModelAndView("project/list");
        setUserAuth(mv);
        UserDTO current = getCurrentUser();

        mv.addObject("projects", projectService.findByUserId(current.getId()));

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

        Project project = projectService.findOne(id);
        mv.addObject("project", project);

        return mv;
    }

    @GetMapping("/project/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        return add(projectService.findOne(id));
    }

    @GetMapping("/project/delete/{id}")
    public ModelAndView deleteView(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("project/list");

        UserDTO current = getCurrentUser();
        List<Project> projects = projectService.findByUserId(current.getId());
        Project project = projectService.findOne(id);

        mv.addObject("projects", projects);
        mv.addObject("project", project);
        mv.addObject("showModalDelete", true);

        return mv;
    }

    @PostMapping("/project/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        // @TODO validar permissões
        if (!isNull(projectService.findOne(id))) {
            projectService.delete(id);
        }

        return "redirect:/project";
    }

    @PostMapping("/project/save")
    public ModelAndView save(@Valid Project project, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            return add(project);
        }

        UserDTO user = getCurrentUser();

        try {
            projectService.save(project, user);
        } catch (BusinessRuleException e) {
            logger.error("error.project.controller.save", e);

            if (e == BusinessExceptions.WITHOUT_ATTRIBUTION) {
                modelAndView.addObject("errorMessage", "Seu perfil de membro não [e permitido!");
            } else if (e == BusinessExceptions.MEMBER_NOT_EXISTS) {
                modelAndView.addObject("errorMessage", "Você não é membro do projeto!");
            } else {
                modelAndView.addObject("errorMessage", "Ops! Houve um problema! :/");
            }
        }

        return findAll();
    }

    // http://localhost:8080/suggestion?searchstr=car

    /**
     * the rest controller which is requested by the autocomplete input field
     * instead of the countries here could also be made a DB request
     *
     * @param searchstr String de busca de projetos.
     * @return A {@link ProjectSuggestionWrapper} object.
     */
    @GetMapping(value = "/project/suggestion", produces = "application/json")
    @ResponseBody
    public ProjectSuggestionWrapper autocompleteSuggestions(@RequestParam("searchstr") String searchstr) {
        logger.debug("searchstr = " + searchstr);

        List<ProjectSuggestion> suggestions = new ArrayList<>();
        UserDTO current = getCurrentUser();
        List<Project> projects = projectService.findByName(current.getId(), searchstr);

        for (Project project : projects) {
            ProjectSuggestion ps = new ProjectSuggestion(project.getName());
            ps.setData(project.getId().toString());
            suggestions.add(ps);
        }

        // truncate the list to the first n, max 20 elements
        int n = Math.min(suggestions.size(), 20);
        List<ProjectSuggestion> sulb = new ArrayList<>(suggestions.subList(0, n));

        ProjectSuggestionWrapper sw = new ProjectSuggestionWrapper();
        sw.setSuggestions(sulb);
        return sw;
    }
}
