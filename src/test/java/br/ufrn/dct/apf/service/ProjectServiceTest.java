package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.model.Project;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectServiceTest extends AbstractTestNGSpringContextTests {

    private SoftAssert softAssert;

    @Autowired
    private ProjectService service;

    //@Test
    public void delete() {
        throw new RuntimeException("Test not implemented");
    }

    //@Test
    public void findAll() {
        List<Project> projetos = service.findAll();
        softAssert.assertNotNull(projetos, "Teste 01");
        softAssert.assertEquals(projetos.size(), 2, "Teste 02");
    }

    //@Test
    public void findOne() {
        throw new RuntimeException("Test not implemented");
    }

    //@Test
    public void save() {
        throw new RuntimeException("Test not implemented");
    }
}
