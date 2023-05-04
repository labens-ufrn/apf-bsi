package br.ufrn.dct.apf.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;

import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.service.ProjectService;
import br.ufrn.dct.apf.service.UserService;
import br.ufrn.dct.apf.service.UserStoryService;

@WebMvcTest(UserStoryController.class)
public class UserStoryControllerTest extends AbstractControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UserStoryService userStoryService;
    
    @MockBean
    private UserStoryService service;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private ProjectService projectService;
    
    @Test
    public void listFromService() throws Exception {
       List<UserStory> value = new ArrayList<>();
       System.err.println(userStoryService);
       when(userStoryService.findAll()).thenReturn(value);
        
        mockMvc.perform(get("/us/list"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andReturn();
//        .andExpect(content().string(containsString("Hello, Mock")));
    }
}
