package br.ufrn.dct.apf.service;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.model.Member;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.repository.RoleRepository;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class TeamServiceTest extends AbstractTestNGSpringContextTests {

    private SoftAssert softAssert;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private RoleRepository roleService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private MemberService memberService;

    private Project p1;
    private User analista, desenvolvedor;
    private Member m1, m2;
    private Role projectOwner, projectDev;

    @BeforeMethod
    public void startTest() {
        softAssert = new SoftAssert();
        
        p1 = new Project();
        
        p1.setName("APF Project");
        p1.setDescription("Analisador de Pontos por Função");
        p1.setCreatedOn(GregorianCalendar.getInstance().getTime());
        
        analista = new User();
        analista.setName("Taciano");
        analista.setLastName("Morais Silva");
        analista.setEmail("tacianosilva@gmai.com");
        analista.setPassword("12345");
        
        desenvolvedor = new User();
        desenvolvedor.setName("Zé");
        desenvolvedor.setLastName("Silva");
        desenvolvedor.setEmail("zesilva@gmai.com");
        desenvolvedor.setPassword("12345");

        projectOwner = new Role();
        projectOwner.setRoleName("Project Owner");
        
        projectDev = new Role();
        projectDev.setRoleName("Project Dev");
    }
    
    @AfterMethod
    public void endTest() {
        softAssert = null;
        memberService.delete(m1.getId());
        memberService.delete(m2.getId());
        projectService.delete(p1.getId());
        userService.delete(analista.getId());
        userService.delete(desenvolvedor.getId());
        roleService.delete(projectOwner.getId());
        roleService.delete(projectDev.getId());
        p1 = null;
    }

/*    //@Test
    public void findAll() {
        List<Project> projetos = service.findAll();
        
        softAssert.assertNotNull(projetos, "T01 - NotNull:");
        softAssert.assertEquals(projetos.size(), 1, "T02 - Equals:");
        
        softAssert.assertAll();
    }
*/
/*    //@Test
    public void findOne() {
        Long id = p1.getId();
        
        Project found = service.findOne(id);
        
        softAssert.assertNotNull(found, "T01 - NotNull:");
        
        softAssert.assertEquals(found.getName(), p1.getName(), "T03 - Equals:");
        softAssert.assertEquals(found.getDescription(), p1.getDescription(), "T04 - Equals:");
        
        softAssert.assertNotNull(found.getCreated(), "T05 - NotNull:");
        softAssert.assertNotNull(found.getActive(), "T06 - NotNull:");
        softAssert.assertEquals(found.getActive(), p1.getActive(), "T07 - Equals:");
        
        softAssert.assertAll();
    }*/

    @Test
    public void save() {

        roleService.save(projectOwner);
        roleService.save(projectDev);

        analista.setNewRole(projectOwner);

        desenvolvedor.setNewRole(projectDev);
        
        userService.save(analista);
        userService.save(desenvolvedor);
        
        projectService.save(p1);
        
        m1 = new Member();
        m1.setUser(analista);
        m1.setProject(p1);
        m1.setCreatedOn(GregorianCalendar.getInstance().getTime());
        
        m2 = new Member();
        m2.setUser(desenvolvedor);
        m2.setProject(p1);
        m2.setCreatedOn(GregorianCalendar.getInstance().getTime());
        
        memberService.save(m1);
        memberService.save(m2);
        
        Project p2 = projectService.findOne(p1.getId());
        Set<Member> team = p2.getTeam();
        for (Member member : team) {
            System.out.println(member.getUser().getName());
            System.out.println(member.getProject().getName());
        }
        
        List<Project> projectByAnalista = projectService.findByUserId(analista.getId());
        for (Project project : projectByAnalista) {
            System.out.println("Project by Analista: " + project.getName());
        }

        softAssert.assertAll();
    }
    
/*    //@Test
    public void update() {
        Long id = p1.getId();
        
        Project update = service.findOne(id);

        update.setName("Novo Projeto");
        update.setDescription("Novo projeto para teste de update");
        
        service.save(update);
        
        Project found = service.findOne(id);
        
        softAssert.assertNotNull(found, "T01 - NotNull:");
        
        softAssert.assertEquals(found.getName(), "Novo Projeto", "T02 - Equals:");
        softAssert.assertEquals(found.getDescription(), "Novo projeto para teste de update", "T03 - Equals:");
        softAssert.assertEquals(found.getActive(), 0, "T04 - Equals:");

        softAssert.assertAll();
    }
    
    //@Test
    public void delete() {
        Project p2 = new Project();
        
        p2.setName("Test Project");
        p2.setDescription("TestNG Project");
        p2.setCreated(GregorianCalendar.getInstance().getTime());
        p2.setActive(1);
        
        service.save(p2);
        
        Long id = p2.getId();
        
        Project found = service.findOne(id);
        List<Project> projetos = service.findAll();
        
        softAssert.assertNotNull(id, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");
        
        softAssert.assertNotNull(projetos, "T03 - NotNull:");
        softAssert.assertEquals(projetos.size(), 2, "T04 - Equals:");
        
        service.delete(id);
        
        found = service.findOne(id);
        projetos = service.findAll();
        
        softAssert.assertNull(found, "T05 - NotNull:");
        
        softAssert.assertNotNull(projetos, "T06 - NotNull:");
        softAssert.assertEquals(projetos.size(), 1, "T07 - Equals:");
        
        softAssert.assertAll();
    }*/
}
