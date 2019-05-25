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

import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.service.BusinessRuleException;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class RoleRepositoryTest extends AbstractTestNGSpringContextTests {

    private SoftAssert softAssert;

    @Autowired
    private RoleRepository repository;

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
        String adminRole = Role.ADMIN_ROLE;

        List<Role> regras = repository.findAll();
        
        for (Role role : regras) {
            System.out.println("Role Name: " + role.getRoleName());
        }
        
        softAssert.assertEquals(regras.size(), 2, "T00 - Equals:");
        
        Role regra = repository.findByRoleName(adminRole);

        softAssert.assertNotNull(regra, "T01 - NotNull:");
        softAssert.assertEquals(regra.getRoleName(), "ADMIN", "T02 - Equals:");
        
        String userRole = Role.USER_ROLE;

        Role user = repository.findByRoleName(userRole);

        softAssert.assertNotNull(user, "T03 - NotNull:");
        softAssert.assertEquals(user.getRoleName(), "USER", "T04 - Equals:");

        softAssert.assertAll();
    }
    
    /**
     * Teste baseado na discussão no link:
     * https://codereview.stackexchange.com/questions/129358/unit-testing-equals-hashcode-and-comparator-asserting-contracts
     * @throws BusinessRuleException
     */
    @Test
    public void equalsAndHashcode() throws BusinessRuleException {
        Role r1, r2, r3;
        
        r1 = new Role();
        r1.setId(10);
        r1.setRoleName("role1");

        r2 = new Role();
        r2.setId(10);
        r2.setRoleName("role1");
        
        r3 = new Role();
        r3.setId(10);
        r3.setRoleName("role1");
        
        UserStory us = new UserStory("Test Project", "TestNG Project");
        us.setId(55L);

        softAssert.assertEquals(r1, r1, "T01 - Equals:TestReflexive");
        softAssert.assertEquals(r1, r2, "T02 - Equals:TestSymmetric");
        softAssert.assertEquals(r2, r1, "T03 - Equals:TestSymmetric");
        softAssert.assertEquals(r1, r2, "T04 - Equals:TestTransitive");
        softAssert.assertEquals(r2, r3, "T05 - Equals:TestTransitive");
        softAssert.assertEquals(r1, r3, "T06 - Equals:TestTransitive");
        softAssert.assertFalse(r1.equals(null), "T07 - Equals:TestNonNullity");
        softAssert.assertFalse(r1.equals(us), "T08 - Equals:TestNonNullity");
        
        softAssert.assertEquals(r1.hashCode(), r1.hashCode(), "T09 - Equals:TestHashCodeConsistency");
        softAssert.assertEquals(r1.hashCode(), r2.hashCode(), "T10 - Equals:TestHashCodeEquality");
        
        r1.setId(0);
        r1.setRoleName(null);
        
        softAssert.assertFalse(r1.equals(r2), "T11 - Equals:TestDifferent");
        softAssert.assertFalse(r1.equals(r3), "T12 - Equals:TestDifferent");
        
        r1.setId(11);
        r1.setRoleName("role1");
        
        r2.setId(12);
        r2.setRoleName("role2");
        
        r3.setId(11);
        r3.setRoleName("role3");
        
        softAssert.assertFalse(r1.equals(r2), "T13 - Equals:TestDifferent");
        softAssert.assertFalse(r1.equals(r3), "T14 - Equals:TestDifferent");
        
        r2.setId(0);
        r3.setRoleName(null);
        
        softAssert.assertFalse(r2.equals(r1), "T15 - Equals:TestDifferent");
        softAssert.assertFalse(r3.equals(r1), "T16 - Equals:TestDifferent");
        
        softAssert.assertNotEquals(r1.hashCode(), r2.hashCode(), "T17 - Equals:TestHashCodeConsistency");
        softAssert.assertNotEquals(r1.hashCode(), r3.hashCode(), "T18 - Equals:TestHashCodeEquality");
        
        softAssert.assertFalse(r1.equals(r2), "T19 - Equals:TestDifferent");
        softAssert.assertFalse(r1.equals(r3), "T20 - Equals:TestDifferent");
        
        r1.setRoleName(null);
        r3.setRoleName(null);

        softAssert.assertTrue(r1.equals(r3), "T21 - Equals:TestEquals");
        softAssert.assertEquals(r1.getId(), r3.getId(), "T22 - Equals:TestEquals");
        softAssert.assertEquals(r2.getRoleName(), r2.toString(), "T23 - Equals:TestEquals");

        softAssert.assertAll();
    }
}
