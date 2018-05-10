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
import br.ufrn.dct.apf.model.UserStory;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class UserStoryServiceTest extends AbstractTestNGSpringContextTests {

    private SoftAssert softAssert;

    @Autowired
    private ProjectService service;

    @Autowired
    private UserStoryService userStoryService;

    private Project p1;

    private UserStory us1, us2;

    @BeforeMethod
    public void startTest() {
        softAssert = new SoftAssert();

        p1 = new Project();

        p1.setName("APF Project");
        p1.setDescription("Analisador de Pontos por Função");
        p1.setCreatedOn(GregorianCalendar.getInstance().getTime());

        service.save(p1);

        us1 = new UserStory("US01", "Manter Projeto");
        us1.setProject(p1);

        us2 = new UserStory("US02", "Manter UserStory");
        us2.setProject(p1);

        userStoryService.save(us1);
        userStoryService.save(us2);
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
        userStoryService.delete(us1.getId());
        userStoryService.delete(us2.getId());
        service.delete(p1.getId());
        p1 = null;
        us1 = null;
        us2 = null;
    }

    @Test
    public void findAll() {
        List<UserStory> uss = userStoryService.findAll();

        softAssert.assertNotNull(uss, "T01 - NotNull:");
        softAssert.assertEquals(uss.size(), 2, "T02 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void findOne() {
        Long id = us1.getId();

        UserStory found = userStoryService.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), us1.getName(), "T03 - Equals:");
        softAssert.assertEquals(found.getDescription(), us1.getDescription(), "T04 - Equals:");
        softAssert.assertEquals(found.getProject().getId(), us1.getProject().getId(), "T04 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void save() {
        UserStory us3 = new UserStory("US03", "Manter User");
        us3.setProject(p1);

        userStoryService.save(us3);

        Long id3 = us3.getId();

        UserStory found = userStoryService.findOne(id3);
        List<UserStory> uss = userStoryService.findAll();

        softAssert.assertNotNull(id3, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(uss, "T03 - NotNull:");
        softAssert.assertEquals(uss.size(), 3, "T04 - Equals:");

        softAssert.assertEquals(found.getName(), us3.getName(), "T05 - Equals:");
        softAssert.assertEquals(found.getDescription(), us3.getDescription(), "T06 - Equals:");
        softAssert.assertEquals(found.getProject().getId(), us3.getProject().getId(), "T07 - Equals:");

        userStoryService.delete(id3);

        softAssert.assertAll();
    }

    @Test
    public void update() {
        Long id = us1.getId();

        UserStory update = userStoryService.findOne(id);

        update.setName("Novo User Story");
        update.setDescription("Novo user story para teste de update");

        userStoryService.save(update);

        UserStory found = userStoryService.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), "Novo User Story", "T02 - Equals:");
        softAssert.assertEquals(found.getDescription(), "Novo user story para teste de update", "T03 - Equals:");
        softAssert.assertEquals(found.getProject().getId(), us1.getProject().getId(), "T04 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void delete() {
        UserStory us4 = new UserStory("US04", "Testando o delete de UserStory");
        us4.setProject(p1);

        userStoryService.save(us4);

        Long id4 = us4.getId();

        UserStory found = userStoryService.findOne(id4);
        List<UserStory> uss = userStoryService.findAll();

        softAssert.assertNotNull(id4, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(uss, "T03 - NotNull:");
        softAssert.assertEquals(uss.size(), 3, "T04 - Equals:");

        userStoryService.delete(id4);

        found = userStoryService.findOne(id4);
        uss = userStoryService.findAll();

        softAssert.assertNull(found, "T05 - isNull:");

        softAssert.assertNotNull(uss, "T06 - NotNull:");
        softAssert.assertEquals(uss.size(), 2, "T07 - Equals:");

        softAssert.assertAll();
    }
}
