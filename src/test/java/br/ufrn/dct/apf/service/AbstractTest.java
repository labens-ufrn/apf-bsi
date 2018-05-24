package br.ufrn.dct.apf.service;

import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import br.ufrn.dct.apf.model.EIF;
import br.ufrn.dct.apf.model.ILF;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.model.UserStory;

public class AbstractTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private UserStoryService userStoryService;
    
    protected User createUser(String name, String lastName) {
        User user = new User();

        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(name+lastName+"@gmail.com");
        user.setPassword("12345");
        user.setActive(1);

        return user;
    }
    
    protected Project createProjectAPF() {
        Project apf = new Project();
        
        apf.setName("APF Project");
        apf.setDescription("Sistema Analisador de Pontos por Função");
        apf.setCreatedOn(GregorianCalendar.getInstance().getTime());
        return apf;
    }

    protected Project addUserStoriesInAPF(Project apf) {
        UserStory us1 = new UserStory("US01", "Manter Projeto");
        us1.setProject(apf);
        
        ILF aliProject = createProjectILF();
        us1.addData(aliProject);

        UserStory us2 = new UserStory("US02", "Manter User");
        us2.setProject(apf);
        
        ILF aliUser = createUserILF();
        us2.addData(aliUser);
        
        UserStory us3 = new UserStory("US03", "Manter UserStory");
        us3.setProject(apf);
        
        ILF aliUserStory = createUserStoryILF();
        us3.addData(aliUserStory);
        
        UserStory us4 = new UserStory("US04", "Manter DataFunction");
        us4.setProject(apf);
        
        ILF aliDataFunction = createDataFunctionILF();
        us4.addData(aliDataFunction);
        
        userStoryService.save(us1);
        userStoryService.save(us2);
        userStoryService.save(us3);
        userStoryService.save(us4);

        return apf;
    }
    
    protected ILF createProjectILF() {
        ILF aliProject = new ILF("ALI Projeto");
        // Colocar 2 Record Element Types (RET): Tabelas Project e Member
        aliProject.setRecordElementTypes(2L);
        // Somar os Data Element Types (DET): Atributos 7 (Project) + 4 (Member).
        aliProject.setDataElementTypes(11L);
        return aliProject;
    }
    
    protected ILF createUserILF() {
        ILF aliUser = new ILF("ALI User");
        // Colocar 2 Record Element Types (RET): Tabelas User e Role
        aliUser.setRecordElementTypes(2L);
        // Somar os Data Element Types (DET): Atributos 7 (user) + 2 (role).
        aliUser.setDataElementTypes(11L);
        return aliUser;
    }
    
    protected ILF createUserStoryILF() {
        ILF aliUserStory = new ILF("ALI UserStory");
        // Colocar 1 Record Element Types (RET): Tabela UserStory
        aliUserStory.setRecordElementTypes(1L);
        // Somar os Data Element Types (DET): Atributos 5 (UserStory).
        aliUserStory.setDataElementTypes(5L);
        return aliUserStory;
    }
    
    protected ILF createDataFunctionILF() {
        ILF aliDataFunction = new ILF("ALI DataFunction");
        // Colocar 1 Record Element Types (RET): Tabela DataFunction
        aliDataFunction.setRecordElementTypes(1L);
        // Somar os Data Element Types (DET): Atributos 6 (DataFunction).
        aliDataFunction.setDataElementTypes(11L);
        return aliDataFunction;
    }
    
    protected EIF createAIE() {
        EIF aieEndereco = new EIF("AIE Endereco");
        // Colocar 1 Record Element Types (RET): Tabelas Endereço
        aieEndereco.setRecordElementTypes(1L);
        // Somar os Data Element Types (DET): Atributos 7 (endereco).
        aieEndereco.setDataElementTypes(7L);
        return aieEndereco;
    }
    
    protected UserStory createStoryWithILF() {
        UserStory us = new UserStory("US01", "User Story 01 with ILF");
        ILF ali = createILF();
        us.addData(ali);
        return us;
    }
    
    protected UserStory createStoryWithEIF() {
        UserStory us = new UserStory("US02", "User Story 02 with EIF");
        EIF aie = createEIF();
        us.addData(aie);
        return us;
    }
    
    protected UserStory createStoryWithILFandEIF() {
        UserStory us = new UserStory("US03", "User Story 03 with ILF and EIF");
        ILF ali = createILF();
        EIF aie = createEIF();
        us.addData(ali);
        us.addData(aie);
        return us;
    }
    
    protected ILF createILF() {
        ILF ali = new ILF("ALI");
        //2 Record Element Types (RET): Two Table.
        ali.setRecordElementTypes(2L);
        //Data Element Types (DET): Atributos 7 (Table 1)  + 4 (Table 2).
        ali.setDataElementTypes(11L);
        return ali;
    }
    
    protected EIF createEIF() {
        EIF aie = new EIF("AIE");
        //1 Record Element Types (RET): Table 1
        aie.setRecordElementTypes(1L);
        //Data Element Types (DET): Atributos 10 (Table 1).
        aie.setDataElementTypes(10L);
        return aie;
    }
}
