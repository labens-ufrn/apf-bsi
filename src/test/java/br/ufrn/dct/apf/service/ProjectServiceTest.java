package br.ufrn.dct.apf.service;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.model.Project;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
// @EnableJpaRepositories
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectServiceTest extends AbstractTestNGSpringContextTests {

    private SoftAssert softAssert;

    @Autowired
    private ProjectService service;

    @BeforeMethod
    public void startTest() {
        softAssert = new SoftAssert();
    }

    // @Test
    public void delete() {
        throw new RuntimeException("Test not implemented");
    }

    @Test
    public void findAll() {
        Project p1 = new Project();
        
        p1.setName("APF Project");
        p1.setDescription("Analisador de Pontos por Função");
        p1.setCreated(GregorianCalendar.getInstance().getTime());
        
        service.save(p1);
        
        List<Project> projetos = service.findAll();
        
        softAssert.assertNotNull(projetos, "T01 - NotNull:");
        softAssert.assertEquals(projetos.size(), 1, "T02 - Equals:");
        
        softAssert.assertAll();
    }

    @Test
    public void findOne() {
        Project p1 = new Project();
        
        p1.setName("APF Project");
        p1.setDescription("Analisador de Pontos por Função");
        p1.setCreated(GregorianCalendar.getInstance().getTime());
        p1.setActive(1);
        
        service.save(p1);
        
        Long id = p1.getId();
        
        Project found = service.findOne(id);
        
        softAssert.assertNotNull(found, "T01 - NotNull:");
        
        softAssert.assertEquals(found.getName(), p1.getName(), "T03 - Equals:");
        softAssert.assertEquals(found.getDescription(), p1.getDescription(), "T04 - Equals:");
        
        softAssert.assertNotNull(found.getCreated(), "T05 - NotNull:");
        softAssert.assertNotNull(found.getActive(), "T06 - NotNull:");
        softAssert.assertEquals(found.getActive(), p1.getActive(), "T07 - Equals:");
        
        softAssert.assertAll();
    }

    // @Test
    public void save() {
        throw new RuntimeException("Test not implemented");
    }
}
