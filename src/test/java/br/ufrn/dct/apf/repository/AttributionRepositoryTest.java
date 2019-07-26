package br.ufrn.dct.apf.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.model.Attribution;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class AttributionRepositoryTest extends AbstractTestNGSpringContextTests {

    private SoftAssert softAssert;

    @Autowired
    private AttributionRepository attribRepository;

    @BeforeMethod
    public void startTest() {
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
    }

    @Test
    public void findByName() {
        String projectManager = Attribution.PROJECT_MANAGER;

        List<Attribution> atribuicoes = attribRepository.findAll();

        for (Attribution attrib : atribuicoes) {
            System.out.println("Attrib Name: " + attrib.getName());
        }

        softAssert.assertEquals(atribuicoes.size(), 2, "T00 - Equals:");

        Attribution manager = attribRepository.findByName(projectManager);

        softAssert.assertNotNull(manager, "T01 - NotNull:");
        softAssert.assertEquals(manager.getName(), "PROJECT MANAGER", "T02 - Equals:");

        String projectMember = Attribution.PROJECT_MEMBER;

        Attribution member = attribRepository.findByName(projectMember);

        softAssert.assertNotNull(member, "T03 - NotNull:");
        softAssert.assertEquals(member.getName(), "PROJECT MEMBER", "T04 - Equals:");

        softAssert.assertAll();
    }
}
