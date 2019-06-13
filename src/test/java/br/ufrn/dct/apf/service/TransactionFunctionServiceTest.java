package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.dto.TransactionFunctionDTO;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.TransactionFunction;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.repository.UserRepository;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class TransactionFunctionServiceTest extends AbstractServiceTest {

    private SoftAssert softAssert;

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserStoryService userStoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionFunctionService transService;

    private Project p1;

    private TransactionFunction tf1, tf2, tf3, tf4;
    
    private TransactionFunctionDTO tfDto1, tfDto2, tfDto3, tfDto4;

    private User manager;
    
    private UserStory us;

    @BeforeMethod
    public void startTest() throws BusinessRuleException {
        softAssert = new SoftAssert();

        p1 = new Project();
        
        p1 = createProject("DF Project Example", "Data Function Project Example");

        manager = createUser("User", "DF");
        userRepository.save(manager);

        projectService.save(p1, manager);
        
        us = new UserStory("US DF", "UserStory para DF");
        us.setProject(p1);
        userStoryService.save(us);

        tfDto1 = createEI("EE1", 1, 10);
        tfDto1.setProject(p1);
        tfDto1.setUserStory(us);

        tfDto2 = createEO("SE1", 1, 10);
        tfDto2.setProject(p1);
        tfDto2.setUserStory(us);
        
        tfDto3 = createEQ("CE1", 1, 10);
        tfDto3.setProject(p1);
        tfDto3.setUserStory(us);
        
        tfDto4 = createEO("SE2", 2, 15);
        tfDto4.setProject(p1);

        tf1 = transService.save(tfDto1);
        tf2 = transService.save(tfDto2);
        tf3 = transService.save(tfDto3);
        tf4 = transService.save(tfDto4);
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
        transService.delete(tf1.getId());
        transService.delete(tf2.getId());
        transService.delete(tf3.getId());
        transService.delete(tf4.getId());
        userStoryService.delete(us.getId());
        projectService.delete(p1.getId());
        userRepository.delete(manager.getId());
        p1 = null;
        tf1 = null;
        tf2 = null;
        tf3 = null;
        tf4 = null;
        manager = null;
    }

    @Test
    public void findAll() {
        List<TransactionFunction> dfs = transService.findAll();

        softAssert.assertNotNull(dfs, "T01 - NotNull:");
        softAssert.assertEquals(dfs.size(), 4, "T02 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void findOne() {
        Long id = tf1.getId();

        TransactionFunction found = transService.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), tf1.getName(), "T03 - Equals:");
        softAssert.assertEquals(found.getDescription(), tf1.getDescription(), "T04 - Equals:");
        softAssert.assertEquals(found.getProject().getId(), tf1.getProject().getId(), "T04 - Equals:");
        softAssert.assertEquals(found.getUserStory().getId(), tf1.getUserStory().getId(), "T05 - Equals:");

        softAssert.assertAll();
    }
    
    @Test
    public void createDtoTransaction() {
        Long id = tf1.getId();

        TransactionFunction found = transService.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), tf1.getName(), "T03 - Equals:");
        softAssert.assertEquals(found.getDescription(), tf1.getDescription(), "T04 - Equals:");
        softAssert.assertEquals(found.getProject().getId(), tf1.getProject().getId(), "T04 - Equals:");
        softAssert.assertEquals(found.getUserStory().getId(), tf1.getUserStory().getId(), "T05 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void save() {
        TransactionFunctionDTO tfDto5 = createEI("EE5", 2, 15);
        tfDto5.setProject(p1);
        tfDto5.setUserStory(us);

        TransactionFunction tf5 = transService.save(tfDto5);

        Long id5 = tf5.getId();

        TransactionFunction found = transService.findOne(id5);
        List<TransactionFunction> tfs = transService.findAll();

        softAssert.assertNotNull(id5, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(tfs, "T03 - NotNull:");
        softAssert.assertEquals(tfs.size(), 5, "T04 - Equals:");

        softAssert.assertEquals(found.getName(), tf5.getName(), "T05 - Equals:");
        softAssert.assertEquals(found.getDescription(), tf5.getDescription(), "T06 - Equals:");
        softAssert.assertEquals(found.getProject().getId(), tf5.getProject().getId(), "T07 - Equals:");
        softAssert.assertEquals(found.getUserStory().getId(), tf5.getUserStory().getId(), "T08 - Equals:");

        transService.delete(id5);

        softAssert.assertAll();
    }

    @Test
    public void update() {
        Long id = tf1.getId();

        TransactionFunction update = transService.findOne(id);

        update.setName("Novo DF");
        update.setDescription("Novo Data Function para teste de update");

        transService.save(update);

        TransactionFunction found = transService.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), "Novo DF", "T02 - Equals:");
        softAssert.assertEquals(found.getDescription(), "Novo Data Function para teste de update", "T03 - Equals:");
        softAssert.assertEquals(found.getProject().getId(), tf1.getProject().getId(), "T04 - Equals:");
        softAssert.assertEquals(found.getUserStory().getId(), tf1.getUserStory().getId(), "T05 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void delete() throws BusinessRuleException {
        TransactionFunctionDTO tfDto6 = createEI("EE6", 1, 8);
        tfDto6.setProject(p1);

        TransactionFunction tf6 = transService.save(tfDto6);

        Long id6 = tf6.getId();

        TransactionFunction found = transService.findOne(id6);
        List<TransactionFunction> tfs = transService.findAll();

        softAssert.assertNotNull(id6, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(tfs, "T03 - NotNull:");
        softAssert.assertEquals(tfs.size(), 5, "T04 - Equals:");
        
        p1 = projectService.findOne(p1.getId());
        
        Boolean t = p1.getTransactionFunctions().contains(found);
        softAssert.assertTrue(t, "T04 - Equals:");
        
        p1.getTransactionFunctions().remove(found);
        found.setProject(null);
        projectService.save(p1, manager);
        
        //transService.delete(id4);

        found = transService.findOne(id6);
        tfs = transService.findAll();

        softAssert.assertNull(found, "T05 - isNull:");

        softAssert.assertNotNull(tfs, "T06 - NotNull:");
        softAssert.assertEquals(tfs.size(), 4, "T07 - Equals:");

        softAssert.assertAll();
    }
    
    @Test
    public void createTransactionFunction() throws BusinessRuleException {
        TransactionFunctionDTO dfDto = createEI("DTO", 1, 8);
        dfDto.setProject(p1);
        dfDto.setUserStory(us);

        TransactionFunction df = transService.save(dfDto);

        Long id = df.getId();

        TransactionFunction found = transService.findOne(id);
        List<TransactionFunction> dfs = transService.findAll();

        softAssert.assertNotNull(id, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(dfs, "T03 - NotNull:");
        softAssert.assertEquals(dfs.size(), 5, "T04 - Equals:");
        
        UserStory usFound = userStoryService.findOne(us.getId());
        
        Boolean usB = usFound.getTransactionFunctions().contains(found);
        softAssert.assertTrue(usB, "T05 - Equals:");
        
        p1 = projectService.findOne(p1.getId());
        
        Boolean t = p1.getTransactionFunctions().contains(found);
        softAssert.assertTrue(t, "T06 - Equals:");
        
        p1.getTransactionFunctions().remove(found);
        found.setProject(null);
        projectService.save(p1, manager);
        
        //transService.delete(id4);

        found = transService.findOne(id);
        dfs = transService.findAll();

        softAssert.assertNull(found, "T07 - isNull:");

        softAssert.assertNotNull(dfs, "T08 - NotNull:");
        softAssert.assertEquals(dfs.size(), 4, "T09 - Equals:");

        softAssert.assertAll();
    }
    
    @Test
    public void equalsAndHashcode() throws BusinessRuleException {
        TransactionFunction tf1 = new TransactionFunction();

        tf1.setId(55L);
        tf1.setName("Test Project");
        tf1.setDescription("TestNG Project");
        tf1.setDataElementTypes(10);
        tf1.setFileTypeReferenced(1);
        tf1.setType(TransactionFunction.TYPE_EI);
        tf1.setProject(p1);
        tf1.setUserStory(us);

        TransactionFunction tf2 = new TransactionFunction();

        tf2.setId(55L);
        tf2.setName("Test Project");
        tf2.setDescription("TestNG Project");
        tf1.setDataElementTypes(10);
        tf1.setFileTypeReferenced(1);
        tf1.setType(TransactionFunction.TYPE_EI);
        tf2.setProject(p1);
        tf2.setUserStory(us);
        
        TransactionFunction tf3 = new TransactionFunction();

        tf3.setId(55L);
        tf3.setName("Test Project");
        tf3.setDescription("TestNG Project");
        tf1.setDataElementTypes(10);
        tf1.setFileTypeReferenced(1);
        tf1.setType(TransactionFunction.TYPE_EI);
        tf3.setProject(p1);
        tf3.setUserStory(us);
        
        UserStory us = new UserStory("Test Project", "TestNG Project");
        us.setId(55L);

        softAssert.assertEquals(tf1, tf1, "T01 - Equals:TestReflexive");
        softAssert.assertEquals(tf1, tf2, "T02 - Equals:TestSymmetric");
        softAssert.assertEquals(tf2, tf1, "T03 - Equals:TestSymmetric");
        softAssert.assertEquals(tf1, tf2, "T04 - Equals:TestTransitive");
        softAssert.assertEquals(tf2, tf3, "T05 - Equals:TestTransitive");
        softAssert.assertEquals(tf1, tf3, "T06 - Equals:TestTransitive");
        softAssert.assertFalse(tf1.equals(null), "T07 - Equals:TestNonNullity");
        softAssert.assertFalse(tf1.equals(us), "T08 - Equals:TestNonNullity");
        
        softAssert.assertEquals(tf1.hashCode(), tf1.hashCode(), "T08 - Equals:TestHashCodeConsistency");
        softAssert.assertEquals(tf1.hashCode(), tf2.hashCode(), "T09 - Equals:TestHashCodeEquality");
        
        tf1.setId(null);
        tf1.setName(null);
        
        softAssert.assertFalse(tf1.equals(tf2), "T10 - Equals:TestDifferent");
        softAssert.assertFalse(tf1.equals(tf3), "T11 - Equals:TestDifferent");
        
        tf1.setId(55L);
        tf1.setName("Test Project");
        tf2.setId(56L);
        tf3.setName("Test Project 3");
        
        softAssert.assertFalse(tf1.equals(tf2), "T12 - Equals:TestDifferent");
        softAssert.assertFalse(tf1.equals(tf3), "T13 - Equals:TestDifferent");
        
        tf2.setId(null);
        tf3.setName(null);
        
        softAssert.assertNotEquals(tf1.hashCode(), tf2.hashCode(), "T08 - Equals:TestHashCodeConsistency");
        softAssert.assertNotEquals(tf1.hashCode(), tf3.hashCode(), "T09 - Equals:TestHashCodeEquality");
        
        softAssert.assertFalse(tf1.equals(tf2), "T14 - Equals:TestDifferent");
        softAssert.assertFalse(tf1.equals(tf3), "T15 - Equals:TestDifferent");

        softAssert.assertFalse(tf3.equals(tf1), "T16 - Equals:TestDifferent");
        
        softAssert.assertEquals(tf1.getDataElementTypes(), 10, "T17 - Equals:TestDifferent");
        softAssert.assertEquals(tf1.getFileTypeReferenced(), 1, "T18 - Equals:TestDifferent");

        softAssert.assertAll();
    }
}
