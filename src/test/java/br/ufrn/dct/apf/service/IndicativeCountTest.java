package br.ufrn.dct.apf.service;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.count.IndicativeCount;
import br.ufrn.dct.apf.model.EIF;
import br.ufrn.dct.apf.model.ILF;
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

    @Autowired
    private UserStoryService userStoryService;

    private Project p1;

    private UserStory us1, us2;

    private User manager;
    
    @BeforeMethod
    public void startTest() throws BusinessRuleException {
        softAssert = new SoftAssert();
        
        count = new IndicativeCount();

        p1 = new Project();

        p1.setName("APF Project");
        p1.setDescription("Analisador de Pontos por Função");
        p1.setCreatedOn(GregorianCalendar.getInstance().getTime());

        manager = createUser("Taciano","Silva");
        userRepository.save(manager);

        projectService.save(p1, manager);

        us1 = new UserStory("US01", "Manter Projeto");
        us1.setProject(p1);

        us2 = new UserStory("US02", "Manter User");
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
        userRepository.delete(manager.getId());
        p1 = null;
        us1 = null;
        us2 = null;
        manager = null;
    }

    @Test
    public void countALIProject() {
        
        ILF aliProject = new ILF("ALI Projeto");
        // Colocar 2 Record Element Types (RET): Tabelas Project e Member
        aliProject.setRecordElementTypes(2L);
        // Somar os Data Element Types (DET): Atributos 7 (Project) + 4 (Member).
        aliProject.setDataElementTypes(11L);
        
        us1.addData(aliProject);
        
        userStoryService.save(us1);
        
        int fp = count.calculeFunctionPoint(us1);

        softAssert.assertNotNull(fp, "T01 - NotNull:");
        softAssert.assertEquals(fp, 35, "T02 - Equals:");

        softAssert.assertAll();
    }
    
    @Test
    public void countALIUser() {
        
        ILF aliUser = new ILF("ALI User");
        // Colocar 2 Record Element Types (RET): Tabelas User e Role
        aliUser.setRecordElementTypes(2L);
        // Somar os Data Element Types (DET): Atributos 7 (user) + 2 (role).
        aliUser.setDataElementTypes(11L);
        
        EIF aieEndereco = new EIF("AIE Endereco");
        // Colocar 1 Record Element Types (RET): Tabelas Endereço
        aieEndereco.setRecordElementTypes(1L);
        // Somar os Data Element Types (DET): Atributos 7 (endereco).
        aieEndereco.setDataElementTypes(7L);
        
        us2.addData(aliUser);
        us2.addData(aieEndereco);
        
        userStoryService.save(us2);
        
        int fp = count.calculeFunctionPoint(us2);

        softAssert.assertNotNull(fp, "T01 - NotNull:");
        softAssert.assertEquals(fp, 50, "T02 - Equals:");

        softAssert.assertAll();
    }
}
