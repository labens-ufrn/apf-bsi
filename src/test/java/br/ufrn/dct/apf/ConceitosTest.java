package br.ufrn.dct.apf;

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
import br.ufrn.dct.apf.model.Member;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.service.BusinessRuleException;
import br.ufrn.dct.apf.service.ProjectService;
import br.ufrn.dct.apf.service.UserService;
import br.ufrn.dct.apf.service.UserStoryService;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class ConceitosTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserStoryService userStoryService;

    private SoftAssert softAssert;

    @BeforeMethod
    public void startTest() {
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
    }

    @Test
    public void conceitos() throws BusinessRuleException {

        System.out.println("Criando o usuário ...");

        UserDTO analista = new UserDTO();
        analista.setName("Taciano");
        analista.setLastName("Silva");
        analista.setEmail("tacianosilva@gmai.com");
        analista.setPassword("12345");
        analista.setActive(1);

        UserDTO developer = new UserDTO();
        developer.setName("Zé");
        developer.setLastName("Silva");
        developer.setEmail("zesilva@gmai.com");
        developer.setPassword("12345");
        developer.setActive(1);

        UserDTO developerMaria = new UserDTO();
        developerMaria.setName("Maria");
        developerMaria.setLastName("Silva");
        developerMaria.setEmail("mariasilva@gmai.com");
        developerMaria.setPassword("12345");
        developerMaria.setActive(1);

        System.out.println("Salvando os usuários ...");

        userService.save(analista);
        userService.save(developer);
        userService.save(developerMaria);

        softAssert.assertNotNull(analista, "T01 - NotNull:");
        softAssert.assertNotNull(analista.getId(), "T02 - NotNull:");

        softAssert.assertNotNull(developer, "T03 - NotNull:");
        softAssert.assertNotNull(developer.getId(), "T04 - NotNull:");

        System.out.println("Usuário salvo! ID = " + analista.getId());
        System.out.println("Usuário salvo! ID = " + developer.getId());
        System.out.println("Usuário salvo! ID = " + developerMaria.getId());

        Project p1 = new Project();

        p1.setName("Sistema APF");
        p1.setDescription("Sistema para auxilio na contagem por Pontos de Função");
        p1.setPrivate(false);
        p1.setCreatedOn(GregorianCalendar.getInstance().getTime());

        System.out.println("Salvando o Projeto ...");

        projectService.save(p1, analista);

        System.out.println("Adicionar novo membro ao Projeto ID = " + p1.getId());

        projectService.addMember(p1, developer, analista);

        Project p2 = new Project();

        p2.setName("Sistema do Developer");
        p2.setDescription("Sistema para Teste");
        p2.setPrivate(false);
        p2.setCreatedOn(GregorianCalendar.getInstance().getTime());

        projectService.save(p2, developerMaria);
        System.out.println("Salvando o Projeto ID = " + p2.getId());

        System.out.println("Projetos por Usuário ...");
        List<Project> projAnalista = projectService.findByUserId(analista.getId());
        for (Project proj : projAnalista) {
            System.out.println("User = " + analista + "ID = " + analista.getId() + " Projeto ID = " + proj.getId());
        }

        List<Project> projDev = projectService.findByUserId(developer.getId());
        for (Project proj : projDev) {
            System.out.println("User = " + developer + "ID = " + developer.getId() + " Projeto ID = " + proj.getId());
        }

        List<Project> projMaria = projectService.findByUserId(developerMaria.getId());
        for (Project proj : projMaria) {
            System.out.println("User = " + developerMaria + "ID = " + developerMaria.getId() + " Projeto ID = " + proj.getId());
        }

        System.out.println("Criando o User Stories ...");

        UserStory us1 = new UserStory("US01", "Manter Projeto");
        us1.setProject(p1);

        UserStory us2 = new UserStory("US02", "Manter UserStory");
        us2.setProject(p1);

        System.out.println("Adicionando ALI ...");
        DataFunction aliUS = DataFunction.createILF("ALI UserStory");
        // Colocar 1 Record Element Types (RET): UserStory
        aliUS.setRecordElementTypes(1L);
        // numero de Data Element Types (DET): 7
        aliUS.setDataElementTypes(7L);

        aliUS.setProject(p1);

        us1.addData(aliUS);

        UserStory us3 = new UserStory("US03", "Manter User");
        us3.setProject(p1);

        System.out.println("Salvando os User Stories ...");

        userStoryService.save(us1);
        userStoryService.save(us2);
        userStoryService.save(us3);

        System.out.println("Adicionando ALI ...");
        DataFunction aliProject = DataFunction.createILF("ALI Projeto");
        // Colocar 2 Record Element Types (RET): Project e Member
        aliProject.setRecordElementTypes(2L);
        // Somar os Data Element Types (DET): 7 + 4.
        aliProject.setDataElementTypes(11L);

        aliProject.setProject(p1);

        us1.addData(aliProject);

        userStoryService.save(us1);

        System.out.println("Recarregar Projeto ID = " + p1.getId());

        Project project = projectService.findOne(p1.getId());

        System.out.println("Listar os Membros do Projeto ID = " + project.getId());

        Set<Member> team = project.getTeam();

        for (Member member : team) {
            System.out.println("Membro ID = " + member.getId() + ", Name = " + member.getUser().getName());
        }

        System.out.println("Listar os User Stories do Projeto ID = " + project.getId());

        Set<UserStory> stories = project.getUserStories();

        for (UserStory us : stories) {
            System.out.println("US ID = " + us.getId() + ", Name = " + us.getName());

            Set<DataFunction> dfs = us.getDataFunctions();
            for (DataFunction data : dfs) {
                System.out.println("Data ID = " + data.getId() + ", Name = " + data.getName() + ", Type = " + data.getType());
                softAssert.assertEquals(data.getUserStory(), us, "Equals - Conceitos - df.us == us");
            }
        }

        System.out.println("Apagando o user stories ...");
        userStoryService.delete(us1.getId());
        userStoryService.delete(us2.getId());
        userStoryService.delete(us3.getId());

        System.out.println("Apagando o projeto ...");
        projectService.delete(p1.getId());
        projectService.delete(p2.getId());

        System.out.println("Apagando o usuário ...");
        userService.delete(analista.getId());
        userService.delete(developer.getId());
        userService.delete(developerMaria.getId());

        softAssert.assertAll();
    }
}
