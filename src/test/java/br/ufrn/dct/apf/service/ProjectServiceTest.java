package br.ufrn.dct.apf.service;

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

import br.ufrn.dct.apf.model.Project;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
// @EnableJpaRepositories
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectServiceTest extends AbstractTestNGSpringContextTests {

    private SoftAssert softAssert;

    @Autowired
    private ProjectService service;

    private Project p1;

    @BeforeMethod
    public void startTest() {
        softAssert = new SoftAssert();

        p1 = new Project();

        p1.setName("APF Project");
        p1.setDescription("Analisador de Pontos por Função");
        p1.setCreatedOn(GregorianCalendar.getInstance().getTime());

        service.save(p1);
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
        service.delete(p1.getId());
        p1 = null;
    }

    @Test
    public void findAll() {
        List<Project> projetos = service.findAll();

        softAssert.assertNotNull(projetos, "T01 - NotNull:");
        softAssert.assertEquals(projetos.size(), 1, "T02 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void findOne() {
        Long id = p1.getId();

        Project found = service.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), p1.getName(), "T03 - Equals:");
        softAssert.assertEquals(found.getDescription(), p1.getDescription(), "T04 - Equals:");

        softAssert.assertNotNull(found.getCreatedOn(), "T05 - NotNull:");
        softAssert.assertNotNull(found.getActive(), "T06 - NotNull:");
        softAssert.assertEquals(found.getActive(), p1.getActive(), "T07 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void save() {
        Project p2 = new Project();

        p2.setName("Test Project");
        p2.setDescription("TestNG Project");
        p2.setCreatedOn(GregorianCalendar.getInstance().getTime());
        p2.setActive(1);

        service.save(p2);

        Long id2 = p2.getId();

        Project found = service.findOne(id2);
        List<Project> projetos = service.findAll();

        softAssert.assertNotNull(id2, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(projetos, "T03 - NotNull:");
        softAssert.assertEquals(projetos.size(), 2, "T04 - Equals:");

        softAssert.assertEquals(found.getName(), p2.getName(), "T05 - Equals:");
        softAssert.assertEquals(found.getDescription(), p2.getDescription(), "T06 - Equals:");

        softAssert.assertNotNull(found.getCreatedOn(), "T07 - NotNull:");
        softAssert.assertNotNull(found.getActive(), "T08 - NotNull:");
        softAssert.assertEquals(found.getActive(), p2.getActive(), "T08 - Equals:");

        service.delete(id2);

        softAssert.assertAll();
    }

    @Test
    public void update() {
        Long id = p1.getId();

        Project update = service.findOne(id);

        update.setName("Novo Projeto");
        update.setDescription("Novo projeto para teste de update");

        service.save(update);

        Project found = service.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), "Novo Projeto", "T02 - Equals:");
        softAssert.assertEquals(found.getDescription(), "Novo projeto para teste de update", "T03 - Equals:");
        softAssert.assertEquals(found.getActive(), 0, "T04 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void delete() {
        Project p2 = new Project();

        p2.setName("Test Project");
        p2.setDescription("TestNG Project");
        p2.setCreatedOn(GregorianCalendar.getInstance().getTime());
        p2.setActive(1);

        service.save(p2);

        Long id2 = p2.getId();

        Project found = service.findOne(id2);
        List<Project> projetos = service.findAll();

        softAssert.assertNotNull(id2, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(projetos, "T03 - NotNull:");
        softAssert.assertEquals(projetos.size(), 2, "T04 - Equals:");

        service.delete(id2);

        found = service.findOne(id2);
        projetos = service.findAll();

        softAssert.assertNull(found, "T05 - isNull:");

        softAssert.assertNotNull(projetos, "T06 - NotNull:");
        softAssert.assertEquals(projetos.size(), 1, "T07 - Equals:");

        softAssert.assertAll();
    }
}
