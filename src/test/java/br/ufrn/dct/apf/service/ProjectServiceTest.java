package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
        List<Project> projetos = service.findAll();
        softAssert.assertNotNull(projetos, "Teste 01");
        softAssert.assertEquals(projetos.size(), 2, "Teste 02");
        
        softAssert.assertAll();
    }

    // @Test
    public void findOne() {
        throw new RuntimeException("Test not implemented");
    }

    // @Test
    public void save() {
        throw new RuntimeException("Test not implemented");
    }
}
