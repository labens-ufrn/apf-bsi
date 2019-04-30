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
}
