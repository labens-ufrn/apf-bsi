package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.dto.DataFunctionDTO;
import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.repository.UserRepository;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class DataFunctionServiceTest extends AbstractServiceTest {

    private SoftAssert softAssert;

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserStoryService userStoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataFunctionService dataService;

    private Project p1;

    private DataFunction df1, df2;
    
    private DataFunctionDTO dfDto1, dfDto2;

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

        dfDto1 = createILF("DF1", 1L, 10L);
        dfDto1.setProject(p1);
        dfDto1.setUserStory(us);

        dfDto2 = createEIF("DF2", 1L, 10L);
        dfDto2.setProject(p1);

        df1 = dataService.save(dfDto1);
        df2 = dataService.save(dfDto2);
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
        dataService.delete(df1.getId());
        dataService.delete(df2.getId());
        userStoryService.delete(us.getId());
        projectService.delete(p1.getId());
        userRepository.delete(manager.getId());
        p1 = null;
        df1 = null;
        df2 = null;
        manager = null;
    }

    @Test
    public void findAll() {
        List<DataFunction> dfs = dataService.findAll();

        softAssert.assertNotNull(dfs, "T01 - NotNull:");
        softAssert.assertEquals(dfs.size(), 2, "T02 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void findOne() {
        Long id = df1.getId();

        DataFunction found = dataService.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), df1.getName(), "T03 - Equals:");
        softAssert.assertEquals(found.getDescription(), df1.getDescription(), "T04 - Equals:");
        softAssert.assertEquals(found.getProject().getId(), df1.getProject().getId(), "T04 - Equals:");
        softAssert.assertEquals(found.getUserStory().getId(), df1.getUserStory().getId(), "T05 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void save() {
        DataFunctionDTO dfDto3 = createILF("DF3", 2L, 15L);
        dfDto3.setProject(p1);
        dfDto3.setUserStory(us);

        DataFunction df3 = dataService.save(dfDto3);

        Long id3 = df3.getId();

        DataFunction found = dataService.findOne(id3);
        List<DataFunction> dfs = dataService.findAll();

        softAssert.assertNotNull(id3, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(dfs, "T03 - NotNull:");
        softAssert.assertEquals(dfs.size(), 3, "T04 - Equals:");

        softAssert.assertEquals(found.getName(), df3.getName(), "T05 - Equals:");
        softAssert.assertEquals(found.getDescription(), df3.getDescription(), "T06 - Equals:");
        softAssert.assertEquals(found.getProject().getId(), df3.getProject().getId(), "T07 - Equals:");
        softAssert.assertEquals(found.getUserStory().getId(), df3.getUserStory().getId(), "T08 - Equals:");

        dataService.delete(id3);

        softAssert.assertAll();
    }

    @Test
    public void update() {
        Long id = df1.getId();

        DataFunction update = dataService.findOne(id);

        update.setName("Novo DF");
        update.setDescription("Novo Data Function para teste de update");

        dataService.save(update);

        DataFunction found = dataService.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), "Novo DF", "T02 - Equals:");
        softAssert.assertEquals(found.getDescription(), "Novo Data Function para teste de update", "T03 - Equals:");
        softAssert.assertEquals(found.getProject().getId(), df1.getProject().getId(), "T04 - Equals:");
        softAssert.assertEquals(found.getUserStory().getId(), df1.getUserStory().getId(), "T05 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void delete() throws BusinessRuleException {
        DataFunctionDTO dfDto4 = createILF("DF4", 1L, 8L);
        dfDto4.setProject(p1);

        DataFunction df4 = dataService.save(dfDto4);

        Long id4 = df4.getId();

        DataFunction found = dataService.findOne(id4);
        List<DataFunction> dfs = dataService.findAll();

        softAssert.assertNotNull(id4, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(dfs, "T03 - NotNull:");
        softAssert.assertEquals(dfs.size(), 3, "T04 - Equals:");
        
        p1 = projectService.findOne(p1.getId());
        
        Boolean t = p1.getDataFunctions().contains(found);
        softAssert.assertTrue(t, "T04 - Equals:");
        
        p1.getDataFunctions().remove(found);
        found.setProject(null);
        projectService.save(p1, manager);
        
        //dataService.delete(id4);

        found = dataService.findOne(id4);
        dfs = dataService.findAll();

        softAssert.assertNull(found, "T05 - isNull:");

        softAssert.assertNotNull(dfs, "T06 - NotNull:");
        softAssert.assertEquals(dfs.size(), 2, "T07 - Equals:");

        softAssert.assertAll();
    }
    
    @Test
    public void createDataFunction() throws BusinessRuleException {
        DataFunctionDTO dfDto = createILF("DTO", 1L, 8L);
        dfDto.setProject(p1);
        dfDto.setUserStory(us);

        DataFunction df = dataService.save(dfDto);

        Long id = df.getId();

        DataFunction found = dataService.findOne(id);
        List<DataFunction> dfs = dataService.findAll();

        softAssert.assertNotNull(id, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(dfs, "T03 - NotNull:");
        softAssert.assertEquals(dfs.size(), 3, "T04 - Equals:");
        
        UserStory usFound = userStoryService.findOne(us.getId());
        
        Boolean usB = usFound.getDataFunctions().contains(found);
        softAssert.assertTrue(usB, "T05 - Equals:");
        
        p1 = projectService.findOne(p1.getId());
        
        Boolean t = p1.getDataFunctions().contains(found);
        softAssert.assertTrue(t, "T06 - Equals:");
        
        p1.getDataFunctions().remove(found);
        found.setProject(null);
        projectService.save(p1, manager);
        
        //dataService.delete(id4);

        found = dataService.findOne(id);
        dfs = dataService.findAll();

        softAssert.assertNull(found, "T07 - isNull:");

        softAssert.assertNotNull(dfs, "T08 - NotNull:");
        softAssert.assertEquals(dfs.size(), 2, "T09 - Equals:");

        softAssert.assertAll();
    }
    
    @Test
    public void equalsAndHashcode() throws BusinessRuleException {
        DataFunction df1 = new DataFunction();

        df1.setId(55L);
        df1.setName("Test Project");
        df1.setDescription("TestNG Project");
        df1.setDataElementTypes(10L);
        df1.setRecordElementTypes(1L);
        df1.setType(DataFunction.TYPE_ILF);
        df1.setProject(p1);
        df1.setUserStory(us);

        DataFunction df2 = new DataFunction();

        df2.setId(55L);
        df2.setName("Test Project");
        df2.setDescription("TestNG Project");
        df2.setDataElementTypes(10L);
        df2.setRecordElementTypes(1L);
        df2.setType(DataFunction.TYPE_ILF);
        df2.setProject(p1);
        df2.setUserStory(us);
        
        DataFunction df3 = new DataFunction();

        df3.setId(55L);
        df3.setName("Test Project");
        df3.setDescription("TestNG Project");
        df3.setDataElementTypes(10L);
        df3.setRecordElementTypes(1L);
        df3.setType(DataFunction.TYPE_ILF);
        df3.setProject(p1);
        df3.setUserStory(us);
        
        UserStory us = new UserStory("Test Project", "TestNG Project");
        us.setId(55L);

        softAssert.assertEquals(df1, df1, "T01 - Equals:TestReflexive");
        softAssert.assertEquals(df1, df2, "T02 - Equals:TestSymmetric");
        softAssert.assertEquals(df2, df1, "T03 - Equals:TestSymmetric");
        softAssert.assertEquals(df1, df2, "T04 - Equals:TestTransitive");
        softAssert.assertEquals(df2, df3, "T05 - Equals:TestTransitive");
        softAssert.assertEquals(df1, df3, "T06 - Equals:TestTransitive");
        softAssert.assertFalse(df1.equals(null), "T07 - Equals:TestNonNullity");
        softAssert.assertFalse(df1.equals(us), "T08 - Equals:TestNonNullity");
        
        softAssert.assertEquals(df1.hashCode(), df1.hashCode(), "T08 - Equals:TestHashCodeConsistency");
        softAssert.assertEquals(df1.hashCode(), df2.hashCode(), "T09 - Equals:TestHashCodeEquality");
        
        df1.setId(null);
        df1.setName(null);
        
        softAssert.assertFalse(df1.equals(df2), "T10 - Equals:TestDifferent");
        softAssert.assertFalse(df1.equals(df3), "T11 - Equals:TestDifferent");
        
        df1.setId(55L);
        df1.setName("Test Project");
        df2.setId(56L);
        df3.setName("Test Project 3");
        
        softAssert.assertFalse(df1.equals(df2), "T12 - Equals:TestDifferent");
        softAssert.assertFalse(df1.equals(df3), "T13 - Equals:TestDifferent");
        
        df2.setId(null);
        df3.setName(null);
        
        softAssert.assertNotEquals(df1.hashCode(), df2.hashCode(), "T08 - Equals:TestHashCodeConsistency");
        softAssert.assertNotEquals(df1.hashCode(), df3.hashCode(), "T09 - Equals:TestHashCodeEquality");
        
        softAssert.assertFalse(df1.equals(df2), "T14 - Equals:TestDifferent");
        softAssert.assertFalse(df1.equals(df3), "T15 - Equals:TestDifferent");

        softAssert.assertAll();
    }
}
