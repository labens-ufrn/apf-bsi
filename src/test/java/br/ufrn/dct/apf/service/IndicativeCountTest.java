package br.ufrn.dct.apf.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.count.IndicativeCount;
import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.repository.UserRepository;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class IndicativeCountTest extends AbstractTest {

    private SoftAssert softAssert;

    IndicativeCount count;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    private Project apf;

    private User manager;

    @BeforeMethod
    public void startTest() throws BusinessRuleException {
        softAssert = new SoftAssert();

        count = new IndicativeCount();

        apf = createProjectAPF();

        //Create User Manager
        manager = createUser("Taciano","Silva");
        userRepository.save(manager);

        //Save Project with Manager
        projectService.save(apf, manager);

        addUserStoriesInAPF(apf);

        //Update Project - Add User Stories
        //apf = projectService.save(apf, manager);

        //Reload APF Project
        apf = projectService.findOne(apf.getId());
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
        //userStoryService.delete(us1.getId());
        //userStoryService.delete(us2.getId());
        projectService.delete(apf.getId());
        userRepository.delete(manager.getId());
        apf = null;
        manager = null;
    }

    @Test
    public void countUserStories() throws BusinessRuleException {

        Set<UserStory> uss = apf.getUserStories();

        for (UserStory userStory : uss) {
            int fp = count.calculeFunctionPoint(userStory);
            softAssert.assertNotNull(fp, "T01 - NotNull: " + userStory.getName());
            
            Set<DataFunction> data = userStory.getDataFunctions();
            for (DataFunction df : data) {
                int fpdf = count.calculeFunctionPoint(df);
                softAssert.assertNotNull(fpdf, "T02 - NotNull: " + df.getName());
                softAssert.assertEquals(df.getUserStory(), userStory, "T03 - Equals: df.us == us");
                softAssert.assertNotNull(df, "T04 - NotNull: " + df.getDataElementTypes());
                softAssert.assertNotNull(df, "T05 - NotNull: " + df.getRecordElementTypes());
            }
        }

        softAssert.assertAll();
    }

    @Test
    public void countAPF() throws BusinessRuleException {

        int fp = count.calculeFunctionPoint(apf);

        softAssert.assertNotNull(fp, "T01 - NotNull:");
        softAssert.assertEquals(fp, 155, "T02 - Function Points: ");

        softAssert.assertAll();
    }

    @Test
    public void countUserStoryWithILF() throws BusinessRuleException {

        UserStory us = createStoryWithILF();

        int fp = count.calculeFunctionPoint(us);

        softAssert.assertNotNull(fp, "T01 - NotNull:");
        softAssert.assertEquals(fp, 35, "T02 - Function Points: ");

        softAssert.assertAll();
    }

    @Test
    public void countUserStoryWithEIF() throws BusinessRuleException {

        UserStory us = createStoryWithEIF();

        int fp = count.calculeFunctionPoint(us);

        softAssert.assertNotNull(fp, "T01 - NotNull:");
        softAssert.assertEquals(fp, 15, "T02 - Function Points: ");

        softAssert.assertAll();
    }

    @Test
    public void countUserStoryWithILFandEIF() throws BusinessRuleException {

        UserStory us = createStoryWithILFandEIF();

        int fp = count.calculeFunctionPoint(us);

        softAssert.assertNotNull(fp, "T01 - NotNull:");
        softAssert.assertEquals(fp, 50, "T02 - Function Points: ");

        softAssert.assertAll();
    }
    
    @Test(expectedExceptions = BusinessRuleException.class, expectedExceptionsMessageRegExp = "error.count.indicative.datafunction.not.exists")
    public void countDataFunctionNull() throws BusinessRuleException {
        DataFunction df = null;
        count.calculeFunctionPoint(df);
    }
    
    @Test(expectedExceptions = BusinessRuleException.class, expectedExceptionsMessageRegExp = "error.count.indicative.datafunction.not.exists")
    public void countDataFunctionNotExists() throws BusinessRuleException {
        DataFunction df = DataFunction.createEIF("Nome");
        df.setType("OutroTipo");
        count.calculeFunctionPoint(df);
    }
}
