package br.ufrn.dct.apf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.service.ProjectService;
import br.ufrn.dct.apf.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(LoginController.class)
public class LoginControllerTest extends AbstractControllerTest {

    private SoftAssert softAssert;

    @BeforeMethod
    public void startTest() {
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
    }

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;

    @MockBean
    private ProjectService projectService;

    @Test
	public void getRootURL() throws Exception {
        softAssert.assertNotNull(mockMvc);
        mockMvc.perform(get("/"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("action=\"/login\"")));
	}

    @Test
	public void getRegistrationURL() throws Exception {
        softAssert.assertNotNull(mockMvc);
        mockMvc.perform(get("/registration"))
               .andDo(print())
               .andExpect(status().isOk());
	}
}
