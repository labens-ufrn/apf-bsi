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
import br.ufrn.dct.apf.repository.UserRepository;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class DataFunctionServiceTest extends AbstractServiceTest {

    private SoftAssert softAssert;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataFunctionService dataService;

    private Project p1;

    private DataFunction df1, df2;
    
    private DataFunctionDTO dfDto1, dfDto2;

    private User manager;

    @BeforeMethod
    public void startTest() throws BusinessRuleException {
        softAssert = new SoftAssert();

        p1 = new Project();
        
        p1 = createProject("DF Project Example", "Data Function Project Example");

        manager = createUser("User", "DF");
        userRepository.save(manager);

        projectService.save(p1, manager);

        dfDto1 = createILF("DF1", 1L, 10L);
        dfDto1.setProject(p1);

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

        softAssert.assertAll();
    }

    @Test
    public void save() {
        DataFunctionDTO dfDto3 = createILF("DF3", 2L, 15L);
        dfDto3.setProject(p1);

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
}
