package br.ufrn.dct.apf.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.model.User;

@DataJpaTest
@Rollback(true)
public class UserRepositoryTest  extends AbstractTestNGSpringContextTests {

    private SoftAssert softAssert;

    @Autowired
    private UserRepository repository;

    @BeforeMethod
    public void startTest() {
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("ravikumar@gmail.com");
        user.setPassword("ravi2020");
        user.setFirstName("Ravi");
        user.setLastName("Kumar");

        User savedUser = repository.save(user);

        User existUser = repository.findById(savedUser.getId()).get();

        softAssert.assertEquals(user.getEmail(), existUser.getEmail());

    }
}
