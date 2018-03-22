package br.ufrn.dct.apf.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.service.ProjectService;
import br.ufrn.dct.apf.service.UserStoryService;

@Controller
public class UserStoryController extends AbstractController {

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

            if(result.hasErrors()) {
                return add(us);
            }
            
            User owner = getCurrentUser();
            
            service.save(us);

            return add(null);
        }
        
        /**
         * the rest controller which is requested by the autocomplete input field
         * instead of the countries here could also be made a DB request
         */
        @RequestMapping(value = "/suggestion", method = RequestMethod.GET, produces = "application/json")
        @ResponseBody
        public ProjectSuggestionWrapper autocompleteSuggestions(@RequestParam("searchstr") String searchstr) {
          System.out.println("searchstr: " + searchstr);


          List<Project> suggestions = new ArrayList<>();
          
          List<Project> proj = projectService.findByUserId(null);
          
          //List<Project> proj = projectService.findByName(searchstr);

          suggestions.addAll(proj);

          // truncate the list to the first n, max 20 elements
          int n = suggestions.size() > 20 ? 20 : suggestions.size();
          List<Project> sulb = new ArrayList<>(suggestions.subList(0, n));

          ProjectSuggestionWrapper sw = new ProjectSuggestionWrapper();
          sw.setSuggestions(sulb);
          return sw;
        }


}
