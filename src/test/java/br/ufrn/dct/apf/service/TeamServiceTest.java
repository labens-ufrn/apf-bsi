package br.ufrn.dct.apf.service;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.model.Function;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.Team;
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
    private TeamService teamService;
    
    private Team t1;
    private Project p1;
    private User analista, desenvolvedor;
    private Role projectOwner, projectDev, ROLE_USER, ROLE_ADMIN;

    @BeforeMethod
    public void startTest() {
        softAssert = new SoftAssert();
        
        p1 = new Project();
        
        p1.setName("APF Project");
        p1.setDescription("Analisador de Pontos por Função");
        p1.setCreated(GregorianCalendar.getInstance().getTime());
        
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
        
        ROLE_ADMIN = new Role();
        ROLE_ADMIN.setRole("ADMIN");
        
        ROLE_USER = new Role();
        ROLE_USER.setRole("USER");
        
        roleService.save(ROLE_ADMIN);
        roleService.save(ROLE_USER);
        
        projectOwner = new Role();
        projectOwner.setRole("Project Owner");
        
        projectDev = new Role();
        projectDev.setRole("Project Dev");
    }
    
    @AfterMethod
    public void endTest() {
        softAssert = null;
        teamService.delete(t1.getId());
        projectService.delete(p1.getId());
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
        
        analista.setNewRole(ROLE_ADMIN);
        //analista.setNewRole(ROLE_USER);
        analista.setNewRole(projectOwner);
        
        //desenvolvedor.setNewRole(ROLE_USER);
        desenvolvedor.setNewRole(projectDev);
        
        userService.saveUser(analista);
        userService.saveUser(desenvolvedor);
        
        projectService.save(p1);
        
        t1 = new Team(p1);
        
        Function f1 = new Function();
        f1.setUser(analista);
        f1.setRole(projectOwner);
        f1.setTeam(t1);
        
        Function f2 = new Function();
        f2.setUser(desenvolvedor);
        f2.setRole(projectDev);
        f2.setTeam(t1);
        
        Set<Function> functions = new HashSet<>();
        functions.add(f1);
        functions.add(f2);
        
        t1.setFunctions(functions);
        
        teamService.save(t1);
        
        //Long id = t1.getId();

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
