package br.ufrn.dct.apf;

import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.model.Member;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.service.ProjectService;
import br.ufrn.dct.apf.service.UserService;
import br.ufrn.dct.apf.service.UserStoryService;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class ConceitosTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserStoryService userStoryService;
    
    private SoftAssert softAssert;
    
    @BeforeMethod
    public void startTest() {
        softAssert = new SoftAssert();
    }
    
    @AfterMethod
    public void endTest() {
        softAssert = null;
    }

    @Test
    public void conceitos() {
        
        System.out.println("Criando o usuário ...");
        
        User analista = new User();
        analista.setName("Taciano");
        analista.setLastName("Silva");
        analista.setEmail("tacianosilva@gmai.com");
        analista.setPassword("12345");
        analista.setActive(1);
        
        System.out.println("Salvando o usuário ...");
        
        userService.save(analista);
        
        softAssert.assertNotNull(analista, "T01 - NotNull:");
        softAssert.assertNotNull(analista.getId(), "T01 - NotNull:");
        
        System.out.println("Usuário salvo! ID = "+ analista.getId() );

        Project p1 = new Project();

        p1.setName("Sistema APF");
        p1.setDescription("Sistema para auxilio na contagem por Pontos de Função");
        p1.setCreated(GregorianCalendar.getInstance().getTime());
        
        System.out.println("Salvando o Projeto ...");
        
        projectService.save(p1, analista);

        System.out.println("Criando o User Stories ...");
        
        UserStory us1 = new UserStory("US01", "Manter Projeto");
        us1.setProject(p1);
        UserStory us2 = new UserStory("US02", "Manter UserStory");
        us2.setProject(p1);
        UserStory us3 = new UserStory("US03", "Manter User");
        us3.setProject(p1);
        
        System.out.println("Salvando o User Stories ...");
        
        userStoryService.save(us1);
        userStoryService.save(us2);
        userStoryService.save(us3);
        
        System.out.println("Apagando o user stories ...");
        userStoryService.delete(us1.getId());
        userStoryService.delete(us2.getId());
        userStoryService.delete(us3.getId());
        
        System.out.println("Apagando o projeto ...");
        projectService.delete(p1.getId());
        
        System.out.println("Apagando o usuário ...");
        userService.delete(analista.getId());
        
        softAssert.assertAll();

    }
}
