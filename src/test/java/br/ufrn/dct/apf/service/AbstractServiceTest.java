package br.ufrn.dct.apf.service;

import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import br.ufrn.dct.apf.dto.DataFunctionDTO;
import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.repository.RoleRepository;

public abstract class AbstractServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserStoryService userStoryService;
    
    @Autowired
    private DataFunctionService dataService;

    protected User createUser(String name, String lastName) {
        User user = new User();

        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(name+lastName+"@gmail.com");
        user.setPassword("12345");
        user.setActive(1);
        user.setNewRole(roleRepository.findByRoleName(Role.USER_ROLE));

        return user;
    }

    protected Project createProjectAPF() {
        Project apf = new Project();

        apf.setName("APF Project");
        apf.setDescription("Sistema Analisador de Pontos por Função");
        apf.setPrivate(false);
        apf.setCreatedOn(GregorianCalendar.getInstance().getTime());
        return apf;
    }
    
    /**
     * Create a project object.
     * @param name
     * @param description
     * @return
     */
    protected Project createProject(String name, String description) {
        Project p = new Project();
        p.setName(name);
        p.setDescription(description);
        p.setPrivate(false);
        p.setCreatedOn(GregorianCalendar.getInstance().getTime());
        return p;
    }

    protected Project addUserStoriesInAPF(Project apf) {
        UserStory us1 = new UserStory("US01", "Manter Projeto");
        us1.setProject(apf);

        DataFunction aliProject = createProjectILF();
        aliProject.setProject(apf);
        dataService.save(aliProject);
        us1.addData(aliProject);

        UserStory us2 = new UserStory("US02", "Manter User");
        us2.setProject(apf);

        DataFunction aliUser = createUserILF();
        aliUser.setProject(apf);
        dataService.save(aliUser);
        us2.addData(aliUser);
        
        DataFunction aieEndereco = createAIE();
        aieEndereco.setProject(apf);
        dataService.save(aieEndereco);
        us2.addData(aieEndereco);

        UserStory us3 = new UserStory("US03", "Manter UserStory");
        us3.setProject(apf);

        DataFunction aliUserStory = createUserStoryILF();
        aliUserStory.setProject(apf);
        dataService.save(aliUserStory);
        us3.addData(aliUserStory);

        UserStory us4 = new UserStory("US04", "Manter DataFunction");
        us4.setProject(apf);

        DataFunction aliDataFunction = createDataFunctionILF();
        aliDataFunction.setProject(apf);
        dataService.save(aliDataFunction);
        us4.addData(aliDataFunction);

        userStoryService.save(us1);
        userStoryService.save(us2);
        userStoryService.save(us3);
        userStoryService.save(us4);

        return apf;
    }

    protected DataFunction createProjectILF() {
        DataFunction aliProject = DataFunction.createILF("ALI Projeto");
        // Colocar 2 Record Element Types (RET): Tabelas Project e Member
        aliProject.setRecordElementTypes(2L);
        // Somar os Data Element Types (DET): Atributos 7 (Project) + 4 (Member).
        aliProject.setDataElementTypes(11L);
        return aliProject;
    }

    protected DataFunction createUserILF() {
        DataFunction aliUser = DataFunction.createILF("ALI User");
        // Colocar 2 Record Element Types (RET): Tabelas User e Role
        aliUser.setRecordElementTypes(2L);
        // Somar os Data Element Types (DET): Atributos 7 (user) + 2 (role).
        aliUser.setDataElementTypes(11L);
        return aliUser;
    }

    protected DataFunction createUserStoryILF() {
        DataFunction aliUserStory = DataFunction.createILF("ALI UserStory");
        // Colocar 1 Record Element Types (RET): Tabela UserStory
        aliUserStory.setRecordElementTypes(1L);
        // Somar os Data Element Types (DET): Atributos 5 (UserStory).
        aliUserStory.setDataElementTypes(5L);
        return aliUserStory;
    }

    protected DataFunction createDataFunctionILF() {
        DataFunction aliDataFunction = DataFunction.createILF("ALI DataFunction");
        // Colocar 1 Record Element Types (RET): Tabela DataFunction
        aliDataFunction.setRecordElementTypes(1L);
        // Somar os Data Element Types (DET): Atributos 6 (DataFunction).
        aliDataFunction.setDataElementTypes(11L);
        return aliDataFunction;
    }

    protected DataFunction createAIE() {
        DataFunction aieEndereco = DataFunction.createEIF("AIE Endereco");
        // Colocar 1 Record Element Types (RET): Tabelas Endereço
        aieEndereco.setRecordElementTypes(1L);
        // Somar os Data Element Types (DET): Atributos 7 (endereco).
        aieEndereco.setDataElementTypes(7L);
        return aieEndereco;
    }

    protected UserStory createStoryWithILF() {
        UserStory us = new UserStory("US01", "User Story 01 with ILF");
        DataFunction ali = createILF();
        us.addData(ali);
        return us;
    }

    protected UserStory createStoryWithEIF() {
        UserStory us = new UserStory("US02", "User Story 02 with EIF");
        DataFunction aie = createEIF();
        us.addData(aie);
        return us;
    }

    protected UserStory createStoryWithILFandEIF() {
        UserStory us = new UserStory("US03", "User Story 03 with ILF and EIF");
        DataFunction ali = createILF();
        DataFunction aie = createEIF();
        us.addData(ali);
        us.addData(aie);
        return us;
    }

    protected DataFunction createILF() {
        DataFunction ali = DataFunction.createILF("ALI");
        //2 Record Element Types (RET): Two Table.
        ali.setRecordElementTypes(2L);
        //Data Element Types (DET): Atributos 7 (Table 1)  + 4 (Table 2).
        ali.setDataElementTypes(11L);
        return ali;
    }

    protected DataFunction createEIF() {
        DataFunction aie = DataFunction.createEIF("AIE");
        //1 Record Element Types (RET): Table 1
        aie.setRecordElementTypes(1L);
        //Data Element Types (DET): Atributos 10 (Table 1).
        aie.setDataElementTypes(10L);
        return aie;
    }
    
    protected DataFunctionDTO createILF(String name, Long ret, Long det) {
        DataFunctionDTO ali = new DataFunctionDTO(name, DataFunction.TYPE_ILF);
        //2 Record Element Types (RET).
        ali.setRecordElementTypes(ret);
        //Data Element Types (DET).
        ali.setDataElementTypes(det);
        return ali;
    }
    
    protected DataFunctionDTO createEIF(String name, Long ret, Long det) {
        DataFunctionDTO aie = new DataFunctionDTO(name, DataFunction.TYPE_EIF);
        //2 Record Element Types (RET).
        aie.setRecordElementTypes(ret);
        //Data Element Types (DET).
        aie.setDataElementTypes(det);
        return aie;
    }
}
