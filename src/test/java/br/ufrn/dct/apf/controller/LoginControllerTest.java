package br.ufrn.dct.apf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.service.BusinessRuleException;
import br.ufrn.dct.apf.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest
public class LoginControllerTest extends AbstractTestNGSpringContextTests {
    
    private SoftAssert softAssert;
    
    @Autowired
    private MockMvc mvc;
 
    @Autowired
    private UserService userService;
    
    @Autowired
    private LoginController controller;
    
    @BeforeMethod
    public void startTest() throws BusinessRuleException {
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
    }

	@Test
	public void contextLoads() {
	    softAssert.assertNotNull(controller, "T01 - NotNull");
	    softAssert.assertNotNull(mvc, "T02 - NotNull");
	}
	
	//@Test
	//public void login() throws Exception {
	//    mvc.perform(get("/login"));
	//}
}
