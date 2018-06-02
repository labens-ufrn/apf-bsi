package br.ufrn.dct.apf.repository;

import java.util.GregorianCalendar;
import java.util.List;

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
import br.ufrn.dct.apf.model.User;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class ProjectRepositoryTest extends AbstractTestNGSpringContextTests {
    
    private SoftAssert softAssert;

    @Autowired
    private ProjectRepository repository;
    
    @Autowired
    private UserRepository userRepository;

    private Project p1;
    
    private User user1;

    @BeforeMethod
    public void startTest() {
        softAssert = new SoftAssert();
        
        User manager = createUser();

        p1 = new Project();

        p1.setName("My APF Project");
        p1.setDescription("Analisador de Pontos por Função");
        p1.setCreatedOn(GregorianCalendar.getInstance().getTime());
        
        Member owner = new Member();
        owner.setProject(p1);
        owner.setUser(manager);
        owner.setCreatedOn(GregorianCalendar.getInstance().getTime());
        
        p1.addMember(owner);

        repository.save(p1);
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
        repository.delete(p1.getId());
        userRepository.delete(user1.getId());
        p1 = null;
        user1 = null;
    }

    @Test
    public void findByName() {
        String name = "APF";
        Project p = new Project();
        p.setName(name);
        
        List<Project> projetos = repository.findByTeamUserIdAndNameContaining(user1.getId(), name);

        softAssert.assertNotNull(projetos, "T01 - NotNull:");
        softAssert.assertEquals(projetos.size(), 1, "T02 - Equals:");

        softAssert.assertAll();
    }
    
    private User createUser() {
        user1 = new User();

        user1.setName("Taciano Silva");
        user1.setLastName("Silva");
        user1.setEmail("tacianosilva@gmail.com");
        user1.setPassword("12345");
        user1.setActive(1);

        return userRepository.save(user1);
    }
}
