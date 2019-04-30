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
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.model.Attribution;
import br.ufrn.dct.apf.model.Member;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.repository.AttributionRepository;
import br.ufrn.dct.apf.repository.UserRepository;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class ProjectServiceTest extends AbstractTestNGSpringContextTests {

    private SoftAssert softAssert;

    @Autowired
    private ProjectService service;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AttributionRepository attribRepository;

    private Project p1, p2;

    private User user1, user2, user3;

    @BeforeMethod
    public void startTest() throws BusinessRuleException {
        softAssert = new SoftAssert();

        user1 = createUser("Taciano", "Silva", "tacianosilva@gmail.com");
        user2 = createUser("Zé", "Costa", "zecosta@gmail.com");
        user3 = createUser("User", "3", "user3@gmail.com");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        p1 = new Project();

        p1.setName("APF Project");
        p1.setDescription("Analisador de Pontos por Função");
        p1.setPrivate(false);
        p1.setCreatedOn(GregorianCalendar.getInstance().getTime());

        service.save(p1, user1);

        p2 = new Project();

        p2.setName("Sigest");
        p2.setDescription("Sistemas Gerenciador de Estágios");
        p2.setPrivate(false);
        p2.setCreatedOn(GregorianCalendar.getInstance().getTime());

        service.save(p2, user2);
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
        service.delete(p1.getId());
        service.delete(p2.getId());
        userRepository.delete(user1.getId());
        userRepository.delete(user2.getId());
        userRepository.delete(user3.getId());
        p1 = null;
        p2 = null;
        user1 = null;
        user2 = null;
        user3 = null;
    }

    @Test
    public void findAll() {
        List<Project> projetos = service.findAll();

        softAssert.assertNotNull(projetos, "T01 - NotNull:");
        softAssert.assertEquals(projetos.size(), 2, "T02 - Equals:");

        softAssert.assertAll();
    }
    
    @Test
    public void findByIsPrivate() {
        List<Project> projetos = service.findByIsPrivateFalse();

        softAssert.assertNotNull(projetos, "T01 - NotNull:");
        softAssert.assertEquals(projetos.size(), 2, "T02 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void findOne() {
        Long id = p1.getId();

        Project found = service.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), p1.getName(), "T03 - Equals:");
        softAssert.assertEquals(found.getDescription(), p1.getDescription(), "T04 - Equals:");

        softAssert.assertNotNull(found.getCreatedOn(), "T05 - NotNull:");
        softAssert.assertNotNull(found.getActive(), "T06 - NotNull:");
        softAssert.assertEquals(found.getActive(), p1.getActive(), "T07 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void findByName() {
        String name = "APF";

        List<Project> projetos = service.findByName(user1.getId(), name);

        softAssert.assertNotNull(projetos, "T01 - NotNull:");
        softAssert.assertEquals(projetos.size(), 1, "T02 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void save() throws BusinessRuleException {
        Project p2 = new Project();

        p2.setName("Test Project");
        p2.setDescription("TestNG Project");
        p2.setPrivate(false);
        p2.setCreatedOn(GregorianCalendar.getInstance().getTime());
        p2.setActive(1);

        Member m1 = new Member();
        m1.setUser(user1);
        m1.setProject(p2);
        m1.setAttribution(attribRepository.findByName(Attribution.PROJECT_MANAGER));
        m1.setCreatedOn(GregorianCalendar.getInstance().getTime());
        p2.addMember(m1);

        service.save(p2, user2);

        Long id2 = p2.getId();

        Project found = service.findOne(id2);
        List<Project> projetos = service.findAll();

        softAssert.assertNotNull(id2, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(projetos, "T03 - NotNull:");
        softAssert.assertEquals(projetos.size(), 3, "T04 - Equals:");

        softAssert.assertEquals(found.getName(), p2.getName(), "T05 - Equals:");
        softAssert.assertEquals(found.getDescription(), p2.getDescription(), "T06 - Equals:");

        softAssert.assertNotNull(found.getCreatedOn(), "T07 - NotNull:");
        softAssert.assertNotNull(found.getActive(), "T08 - NotNull:");
        softAssert.assertEquals(found.getActive(), p2.getActive(), "T08 - Equals:");
        softAssert.assertEquals(found.getPrivate(), p2.getPrivate(), "T08 - Equals:");

        service.delete(id2);

        softAssert.assertAll();
    }

    @Test
    public void addMember() throws BusinessRuleException {
        Set<Member> team = p1.getTeam();

        softAssert.assertNotNull(team, "T01 - NotNull:");
        softAssert.assertEquals(team.size(), 1, "T02 - Equals:");
        softAssert.assertEquals(team.toArray()[0], user1, "T03 - Equals:");

        service.addMember(p1, user2, user1);

        team = p1.getTeam();

        softAssert.assertNotNull(team, "T04 - NotNull:");
        softAssert.assertEquals(team.size(), 2, "T05 - Equals:");
        softAssert.assertEquals(team.toArray()[0], user1, "T06 - Equals:");
        softAssert.assertEquals(team.toArray()[1], user2, "T07 - Equals:");
    }

    @Test(expectedExceptions = BusinessRuleException.class, expectedExceptionsMessageRegExp = "error.project.service.member.not.exists")
    public void addMemberException() throws BusinessRuleException {
        service.addMember(p2, user1, user3);
    }

    @Test(expectedExceptions = BusinessRuleException.class, expectedExceptionsMessageRegExp = "error.project.service.project.is.null")
    public void throwException() throws BusinessRuleException {
        service.save(null, null);
    }

    @Test(expectedExceptions = BusinessRuleException.class, expectedExceptionsMessageRegExp = "error.project.service.member.is.null")
    public void throwException2() throws BusinessRuleException {
        Project p3 = new Project();

        p3.setName("Project 3");
        p3.setDescription("Projeto de Teste");
        p3.setCreatedOn(GregorianCalendar.getInstance().getTime());

        service.save(p3, null);
    }

    @Test
    @Ignore
    public void saveTeamEmpty() throws BusinessRuleException {
        Project p3 = new Project();

        p3.setName("Project 3");
        p3.setDescription("Projeto de Teste");
        p3.setCreatedOn(GregorianCalendar.getInstance().getTime());

        service.save(p3, user1);

        service.save(p3, user2);

        service.delete(p3.getId());
    }

    @Test
    public void update() throws BusinessRuleException {
        Long id = p2.getId();

        Project update = service.findOne(id);

        update.setName("Novo Projeto");
        update.setDescription("Novo projeto para teste de update");

        service.save(update, user2);

        Project found = service.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), "Novo Projeto", "T02 - Equals:");
        softAssert.assertEquals(found.getDescription(), "Novo projeto para teste de update", "T03 - Equals:");
        softAssert.assertEquals(found.getActive(), 1, "T04 - Equals:");

        softAssert.assertAll();
    }

    @Test(expectedExceptions = BusinessRuleException.class, expectedExceptionsMessageRegExp = "error.project.service.member.not.exists")
    public void updateByAnotherUser() throws BusinessRuleException {
        Long id = p1.getId();

        Project update = service.findOne(id);

        update.setName("Novo Projeto");
        update.setDescription("Novo projeto para teste de update");

        service.save(update, user3);

        Project found = service.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), "Novo Projeto", "T02 - Equals:");
        softAssert.assertEquals(found.getDescription(), "Novo projeto para teste de update", "T03 - Equals:");
        softAssert.assertEquals(found.getActive(), 1, "T04 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void delete() throws BusinessRuleException {
        Project p2 = new Project();

        p2.setName("Test Project");
        p2.setDescription("TestNG Project");
        p2.setPrivate(false);
        p2.setCreatedOn(GregorianCalendar.getInstance().getTime());
        p2.setActive(1);

        Member m1 = new Member();
        m1.setUser(user1);
        m1.setProject(p2);
        m1.setAttribution(attribRepository.findByName(Attribution.PROJECT_MANAGER));
        m1.setCreatedOn(GregorianCalendar.getInstance().getTime());
        p2.addMember(m1);

        service.save(p2, user2);

        Long id2 = p2.getId();

        Project found = service.findOne(id2);
        List<Project> projetos = service.findAll();

        softAssert.assertNotNull(id2, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(projetos, "T03 - NotNull:");
        softAssert.assertEquals(projetos.size(), 3, "T04 - Equals:");

        service.delete(id2);

        found = service.findOne(id2);
        projetos = service.findAll();

        softAssert.assertNull(found, "T05 - isNull:");

        softAssert.assertNotNull(projetos, "T06 - NotNull:");
        softAssert.assertEquals(projetos.size(), 2, "T07 - Equals:");

        softAssert.assertAll();
    }

    private User createUser(String name, String lastName, String email) {
        User user = new User();

        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword("12345");
        user.setActive(1);

        return user;
    }
}
