package br.ufrn.dct.apf.service;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import br.ufrn.dct.apf.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.model.UserStory;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class UserStoryServiceTest extends AbstractTestNGSpringContextTests {

    private SoftAssert softAssert;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserStoryService userStoryService;

    private Project p1;

    private UserStory us1, us2;

    private UserDTO manager;

    @BeforeMethod
    public void startTest() throws BusinessRuleException {
        softAssert = new SoftAssert();

        p1 = new Project();

        p1.setName("APF Project");
        p1.setDescription("Analisador de Pontos por Função");
        p1.setPrivate(false);
        p1.setCreatedOn(GregorianCalendar.getInstance().getTime());

        manager = createUser();

        projectService.save(p1, manager);

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
        projectService.delete(p1.getId());
        userService.delete(manager.getId());
        p1 = null;
        us1 = null;
        us2 = null;
        manager = null;
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
        softAssert.assertNotNull(found.getId(), "T01.1 - NotNull:");

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

        softAssert.assertEquals(found.getId(), us3.getId(), "T05.1 - Equals:");
        softAssert.assertEquals(found.getName(), us3.getName(), "T05.2 - Equals:");
        softAssert.assertEquals(found.getDescription(), us3.getDescription(), "T06 - Equals:");
        softAssert.assertEquals(found.getProject().getId(), us3.getProject().getId(), "T07 - Equals:");

        userStoryService.delete(id3);

        softAssert.assertAll();
    }

    @Test
    public void saveWithDataFunction() {

        DataFunction aliProject = DataFunction.createILF("ALI Projeto");
        // Colocar 2 Record Element Types (RET): Project e Member
        aliProject.setRecordElementTypes(2L);
        // Somar os Data Element Types (DET): 7 + 4.
        aliProject.setDataElementTypes(11L);

        aliProject.setProject(p1);

        us1.addData(aliProject);

        userStoryService.save(us1);

        Set<DataFunction> dados = us1.getDataFunctions();

        softAssert.assertTrue(dados.contains(aliProject), "T01 - contains");
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
    public void delete() throws BusinessRuleException {
        UserStory us4 = new UserStory("US04", "Testando o delete de UserStory");
        us4.setProject(p1);

        userStoryService.save(us4);

        Long id4 = us4.getId();

        UserStory found = userStoryService.findOne(id4);
        List<UserStory> uss = userStoryService.findAll();

        softAssert.assertNotNull(id4, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(uss, "T03 - NotNull:");
        softAssert.assertEquals(uss.size(), 3, "T04.1 - Equals:");

        p1 = projectService.findOne(p1.getId());
        boolean t = p1.getUserStories().contains(found);
        softAssert.assertTrue(t, "T04.2 - Equals");
        p1.getUserStories().remove(found);
        projectService.save(p1, manager);

        //userStoryService.delete(id4);

        found = userStoryService.findOne(id4);
        uss = userStoryService.findAll();

        softAssert.assertNull(found, "T05 - isNull:");

        softAssert.assertNotNull(uss, "T06 - NotNull:");
        softAssert.assertEquals(uss.size(), 2, "T07 - Equals:");

        softAssert.assertAll();
    }

    private UserDTO createUser() {
        UserDTO user = new UserDTO("Taciano Silva", "Silva", "tacianosilva@gmail.com", "12345", 1);

        userService.save(user);

        return user;
    }

    @Test
    public void equalsAndHashcode() {
        DataFunction aliProject = DataFunction.createILF("ALI Projeto");
        aliProject.setRecordElementTypes(2L);
        aliProject.setDataElementTypes(11L);
        aliProject.setProject(p1);

        UserStory us1 = new UserStory();

        us1.setId(55L);
        us1.setName("Test Project");
        us1.setDescription("TestNG Project");
        us1.addData(aliProject);
        us1.setProject(p1);

        UserStory us2 = new UserStory();

        us2.setId(55L);
        us2.setName("Test Project");
        us2.setDescription("TestNG Project");
        us2.addData(aliProject);
        us2.setProject(p1);

        UserStory us3 = new UserStory();

        us3.setId(55L);
        us3.setName("Test Project");
        us3.setDescription("TestNG Project");
        us3.addData(aliProject);
        us3.setProject(p1);

        DataFunction df = new DataFunction();
        df.setId(55L);
        df.setName("Test Project");
        df.setDescription("TestNG Project");

        softAssert.assertEquals(us1, us1, "T01 - Equals:TestReflexive");
        softAssert.assertEquals(us1, us2, "T02 - Equals:TestSymmetric");
        softAssert.assertEquals(us2, us1, "T03 - Equals:TestSymmetric");
        softAssert.assertEquals(us1, us2, "T04 - Equals:TestTransitive");
        softAssert.assertEquals(us2, us3, "T05 - Equals:TestTransitive");
        softAssert.assertEquals(us1, us3, "T06 - Equals:TestTransitive");
        softAssert.assertFalse(us1.equals(null), "T07 - Equals:TestNonNullity");
        softAssert.assertFalse(us1.equals(df), "T08 - Equals:TestNonNullity");

        softAssert.assertEquals(us1.hashCode(), us1.hashCode(), "T08 - Equals:TestHashCodeConsistency");
        softAssert.assertEquals(us1.hashCode(), us2.hashCode(), "T09 - Equals:TestHashCodeEquality");

        us1.setId(null);
        us1.setName(null);

        softAssert.assertFalse(us1.equals(us2), "T10 - Equals:TestDifferent");
        softAssert.assertFalse(us1.equals(us3), "T11 - Equals:TestDifferent");

        us1.setId(55L);
        us1.setName("Test Project");
        us2.setId(56L);
        us3.setName("Test Project 3");

        softAssert.assertFalse(us1.equals(us2), "T12 - Equals:TestDifferent");
        softAssert.assertFalse(us1.equals(us3), "T13 - Equals:TestDifferent");

        us2.setId(null);
        us3.setName(null);

        softAssert.assertNotEquals(us1.hashCode(), us2.hashCode(), "T08 - Equals:TestHashCodeConsistency");
        softAssert.assertNotEquals(us1.hashCode(), us3.hashCode(), "T09 - Equals:TestHashCodeEquality");

        softAssert.assertFalse(us1.equals(us2), "T14 - Equals:TestDifferent");
        softAssert.assertFalse(us1.equals(us3), "T15 - Equals:TestDifferent");

        softAssert.assertAll();
    }
}
