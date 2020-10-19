package br.ufrn.dct.apf.service;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import br.ufrn.dct.apf.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.repository.AttributionRepository;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class TeamServiceTest extends AbstractTestNGSpringContextTests {

    private SoftAssert softAssert;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AttributionRepository attribRepository;

    private Project p1;
    private User analista, desenvolvedor, dev2;
    private Member m1, m2;
    private Attribution projectOwner, projectDev;

    @BeforeMethod
    public void startTest() throws BusinessRuleException {
        softAssert = new SoftAssert();

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

        dev2 = new User();
        dev2.setName("dev2");
        dev2.setLastName("Silva");
        dev2.setEmail("dev2@gmai.com");
        dev2.setPassword("12345");

        projectOwner = new Attribution();
        projectOwner.setName(Attribution.PROJECT_MANAGER);

        projectDev = new Attribution();
        projectDev.setName(Attribution.PROJECT_DEV);

        attribRepository.save(projectOwner);
        attribRepository.save(projectDev);

        userService.save(analista);
        userService.save(desenvolvedor);

        p1 = new Project();

        p1.setName("APF Project");
        p1.setDescription("Analisador de Pontos por Função");
        p1.setPrivate(false);
        p1.setCreatedOn(GregorianCalendar.getInstance().getTime());

        projectService.save(p1, analista);
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
        projectService.delete(p1.getId());
        userService.delete(analista.getId());
        userService.delete(desenvolvedor.getId());
        attribRepository.deleteById(projectOwner.getId());
        attribRepository.deleteById(projectDev.getId());
        p1 = null;
    }

    @Test
    public void findAll() {
        List<Member> membros = memberService.findAll();

        softAssert.assertNotNull(membros, "T01 - NotNull:");
        softAssert.assertEquals(membros.size(), 1, "T02 - Equals:");
        Member m = membros.get(0);
        softAssert.assertEquals(m.getAttribution().getId(), 1, "T03 - Equals:");
        softAssert.assertEquals(m.getAttribution().getName(), Attribution.PROJECT_MANAGER, "T04 - Equals:");
        softAssert.assertEquals(m.getUser().getId(), analista.getId(), "T05 - Equals:");
        softAssert.assertEquals(m.getProject().getId(), p1.getId(), "T06 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void findOne() {

        Member member = memberService.getMember(p1.getId(), analista.getId());

        Long id = member.getId();

        Member found = memberService.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getProject().getId(), p1.getId(), "T03 - Equals:");
        softAssert.assertEquals(found.getUser().getId(), analista.getId(), "T04 - Equals:");

        softAssert.assertNotNull(found.getCreatedOn(), "T05 - NotNull:");

        softAssert.assertEquals(found.getAttribution().getId(), 1, "T06 - Equals:");
        softAssert.assertEquals(found.getAttribution().getName(), Attribution.PROJECT_MANAGER, "T07 - Equals:");

        Attribution attribManager = attribRepository.findById(1).orElse(null);
        Attribution attribOwner = attribRepository.findByName(Attribution.PROJECT_MANAGER);

        softAssert.assertTrue(found.getAttribution().equals(attribManager), "T08 - Equals:");
        softAssert.assertFalse(found.getAttribution().equals(attribOwner), "T09 - Equals:");
        softAssert.assertFalse(found.getAttribution().equals(null), "T10 - Equals:");
        softAssert.assertTrue(projectOwner.equals(projectOwner), "T11 - Equals:");
        softAssert.assertTrue(projectOwner.equals(attribOwner), "T12 - Equals:");

        List<Attribution> atts = attribRepository.findAll();

        softAssert.assertNotNull(atts, "T13 - NotNull:");
        softAssert.assertEquals(atts.size(), 4, "T14 - Equals:");

        softAssert.assertNotNull(attribOwner.hashCode(), "T15 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void save() throws BusinessRuleException {

        m1 = new Member();
        m1.setUser(analista);
        m1.setProject(p1);
        m1.setAttribution(attribRepository.findByName(Attribution.PROJECT_MANAGER));
        m1.setCreatedOn(GregorianCalendar.getInstance().getTime());

        m2 = new Member();
        m2.setUser(desenvolvedor);
        m2.setProject(p1);
        m2.setAttribution(attribRepository.findByName(Attribution.PROJECT_MEMBER));
        m2.setCreatedOn(GregorianCalendar.getInstance().getTime());

        p1 = projectService.findOne(p1.getId());
        Set<Member> team1 = p1.getTeam();

        softAssert.assertEquals(team1.size(), 1, "T01 - Equals:");
        Member t1 = (Member)(team1.toArray()[0]);
        softAssert.assertEquals(t1.getUser().getId(), analista.getId(), "T02 - Equals:");

        softAssert.assertTrue(t1.equals(m1), "T02.1 - True:");
        softAssert.assertTrue(team1.contains(t1), "T02.2 - True:");
        softAssert.assertTrue(team1.contains(m1), "T02.3 - True:");
        softAssert.assertFalse(team1.contains(m2), "T02.4 - False:");

        for (Member member : team1) {
            System.out.println(member.getUser().getName());
            System.out.println(member.getProject().getName());
        }
        System.out.println("Team 1 contais m1: " + team1.contains(m1));
        System.out.println("Team 1 contais m2: " + team1.contains(m2));

        for (Member member : team1) {
            System.out.println(member.equals(m1));
            System.out.println(member.hashCode() ==  m1.hashCode());
        }

        //p1.addMember(m1);
        p1.addMember(m2);

        projectService.save(p1, analista);

        Project p2 = projectService.findOne(p1.getId());
        Set<Member> team2 = p2.getTeam();

        softAssert.assertEquals(team2.size(), 2, "T03 - Equals:");
        Member t01 = (Member)(team2.toArray()[0]);
        Member t02 = (Member)(team2.toArray()[1]);
        //softAssert.assertEquals(t01.getUser().getId(), analista.getId(), "T04 - Equals:");
        //softAssert.assertEquals(t02.getUser().getId(), desenvolvedor.getId(), "T05 - Equals:");

        softAssert.assertTrue(team2.contains(t01), "T05.3 - True:");
        softAssert.assertTrue(team2.contains(t02), "T05.4 - True:");
        softAssert.assertTrue(team2.contains(m1), "T05.5 - True:");
        softAssert.assertTrue(team2.contains(m2), "T05.6 - True:");

        System.out.println("Team contais m1: " + team2.contains(m1));
        System.out.println("Team contais m2: " + team2.contains(m2));
        for (Member member : team2) {
            System.out.println(member.getUser().getName());
            System.out.println(member.getProject().getName());
        }

        List<Project> projectByAnalista = projectService.findByUserId(analista.getId());
        softAssert.assertEquals(projectByAnalista.size(), 1, "T06 - Equals:");

        for (Project project : projectByAnalista) {
            System.out.println("Project by Analista: " + project.getName());
        }

        Project project = projectByAnalista.get(0);
        softAssert.assertEquals(project.getId(), p1.getId(), "T07 - Equals:");

        Member found = memberService.findOne(t01.getId());

        softAssert.assertEquals(t01, found, "T08 - Equals");
        softAssert.assertNotEquals(null, found, "T08 - Equals");
        softAssert.assertNotEquals(t01, t02, "T08 - Equals");

        softAssert.assertAll();
    }

    @Test
    public void saveMember() throws BusinessRuleException {

        m1 = new Member();
        m1.setUser(analista);
        m1.setProject(p1);
        m1.setAttribution(attribRepository.findByName(Attribution.PROJECT_MEMBER));
        m1.setCreatedOn(GregorianCalendar.getInstance().getTime());

        m2 = new Member();
        m2.setUser(desenvolvedor);
        m2.setProject(p1);
        m2.setAttribution(attribRepository.findByName(Attribution.PROJECT_MEMBER));
        m2.setCreatedOn(GregorianCalendar.getInstance().getTime());

        m1 = memberService.save(m1);

        p1 = projectService.findOne(p1.getId());
        Set<Member> team1 = p1.getTeam();

        softAssert.assertEquals(team1.size(), 1, "T01 - Equals:");
        Member t1 = (Member)(team1.toArray()[0]);
        softAssert.assertEquals(t1.getUser().getId(), analista.getId(), "T02 - Equals:");

        softAssert.assertTrue(t1.equals(m1), "T02.1 - True:");
        softAssert.assertTrue(team1.contains(t1), "T02.2 - True:");
        softAssert.assertTrue(team1.contains(m1), "T02.3 - True:");
        softAssert.assertFalse(team1.contains(m2), "T02.4 - False:");

        for (Member member : team1) {
            System.out.println(member.getUser().getName());
            System.out.println(member.getProject().getName());
        }
        System.out.println("Team 1 contais m1: " + team1.contains(m1));
        System.out.println("Team 1 contais m2: " + team1.contains(m2));

        for (Member member : team1) {
            System.out.println(member.equals(m1));
            System.out.println(member.hashCode() ==  m1.hashCode());
        }

        m2 = memberService.save(m2);

        Project p2 = projectService.findOne(p1.getId());
        Set<Member> team2 = p2.getTeam();

        softAssert.assertEquals(team2.size(), 2, "T03 - Equals:");
        Member t01 = (Member)(team2.toArray()[0]);
        Member t02 = (Member)(team2.toArray()[1]);
        //softAssert.assertEquals(t01.getUser().getId(), analista.getId(), "T04 - Equals:");
        //softAssert.assertEquals(t02.getUser().getId(), desenvolvedor.getId(), "T05 - Equals:");

        softAssert.assertTrue(team2.contains(t01), "T05.3 - True:");
        softAssert.assertTrue(team2.contains(t02), "T05.4 - True:");
        softAssert.assertTrue(team2.contains(m1), "T05.5 - True:");
        softAssert.assertTrue(team2.contains(m2), "T05.6 - True:");

        List<Project> projectByDev = projectService.findByUserId(desenvolvedor.getId());
        softAssert.assertEquals(projectByDev.size(), 1, "T06 - Equals:");

        for (Project project : projectByDev) {
            System.out.println("Project by Dev: " + project.getName());
        }

        Project project = projectByDev.get(0);
        softAssert.assertEquals(project.getId(), p1.getId(), "T07 - Equals:");

        memberService.delete(m1.getId());
        memberService.delete(m2.getId());

        softAssert.assertAll();
    }

    /**
     * Teste baseado na discussão no link:
     * https://codereview.stackexchange.com/questions/129358/unit-testing-equals-hashcode-and-comparator-asserting-contracts
     * @throws BusinessRuleException
     */
    @Test
    public void equalsAndHashcode() throws BusinessRuleException {
        Member m1, m2, m3;

        Project p2 = new Project();

        p2.setName("APF Project");
        p2.setDescription("Analisador de Pontos por Função");
        p2.setPrivate(false);
        p2.setCreatedOn(GregorianCalendar.getInstance().getTime());

        m1 = new Member();
        m1.setUser(analista);
        m1.setProject(p1);
        m1.setAttribution(attribRepository.findByName(Attribution.PROJECT_MEMBER));
        m1.setCreatedOn(GregorianCalendar.getInstance().getTime());

        m2 = new Member();
        m2.setUser(analista);
        m2.setProject(p1);
        m2.setAttribution(attribRepository.findByName(Attribution.PROJECT_MEMBER));
        m2.setCreatedOn(GregorianCalendar.getInstance().getTime());

        m3 = new Member();
        m3.setUser(analista);
        m3.setProject(p1);
        m3.setAttribution(attribRepository.findByName(Attribution.PROJECT_MEMBER));
        m3.setCreatedOn(GregorianCalendar.getInstance().getTime());

        //m1 = memberService.save(m1);

        UserStory us = new UserStory("Test Project", "TestNG Project");
        us.setId(55L);

        softAssert.assertEquals(m1, m1, "T01 - Equals:TestReflexive");
        softAssert.assertEquals(m1, m2, "T02 - Equals:TestSymmetric");
        softAssert.assertEquals(m2, m1, "T03 - Equals:TestSymmetric");
        softAssert.assertEquals(m1, m2, "T04 - Equals:TestTransitive");
        softAssert.assertEquals(m2, m3, "T05 - Equals:TestTransitive");
        softAssert.assertEquals(m1, m3, "T06 - Equals:TestTransitive");
        softAssert.assertFalse(m1.equals(null), "T07 - Equals:TestNonNullity");
        softAssert.assertFalse(m1.equals(us), "T08 - Equals:TestNonNullity");

        softAssert.assertEquals(m1.hashCode(), m1.hashCode(), "T09 - Equals:TestHashCodeConsistency");
        softAssert.assertEquals(m1.hashCode(), m2.hashCode(), "T10 - Equals:TestHashCodeEquality");

        m1.setId(null);
        m1.setUser(null);
        m1.setProject(null);

        softAssert.assertFalse(m1.equals(m2), "T11 - Equals:TestDifferent");
        softAssert.assertFalse(m1.equals(m3), "T12 - Equals:TestDifferent");

        m1.setId(55L);
        m1.setUser(analista);
        m1.setProject(p1);

        m2.setId(56L);
        m2.setUser(desenvolvedor);
        m2.setProject(p1);

        m3.setId(55L);
        m3.setUser(desenvolvedor);
        m3.setProject(p1);

        softAssert.assertFalse(m1.equals(m2), "T13 - Equals:TestDifferent");
        softAssert.assertFalse(m1.equals(m3), "T14 - Equals:TestDifferent");

        m2.setUser(null);
        m3.setProject(null);

        softAssert.assertFalse(m2.equals(m1), "T15 - Equals:TestDifferent");

        softAssert.assertNotEquals(m1.hashCode(), m2.hashCode(), "T16 - Equals:TestHashCodeConsistency");
        softAssert.assertNotEquals(m1.hashCode(), m3.hashCode(), "T17 - Equals:TestHashCodeEquality");

        softAssert.assertFalse(m1.equals(m2), "T18 - Equals:TestDifferent");
        softAssert.assertFalse(m1.equals(m3), "T19 - Equals:TestDifferent");

        m1.setUser(null);
        m1.setProject(null);
        m3.setUser(null);
        m3.setProject(null);

        softAssert.assertFalse(m1.equals(m2), "T20 - Equals:TestDifferent");
        softAssert.assertTrue(m1.equals(m3), "T21 - Equals:TestEquals");

        //memberService.delete(m1.getId());
        //memberService.delete(m2.getId());
        //memberService.delete(m3.getId());

        softAssert.assertAll();
    }
}
