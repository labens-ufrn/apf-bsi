package br.ufrn.dct.apf.service;

import java.util.List;

import br.ufrn.dct.apf.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.ufrn.dct.apf.model.User;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    private SoftAssert softAssert;

    @Autowired
    private UserService userService;

    private UserDTO user1;

    @BeforeMethod
    public void startTest() {
        softAssert = new SoftAssert();

        user1 = new UserDTO("Taciano Silva", "Silva", "tacianosilva@gmail.com", "12345", 1);

        userService.save(user1);
    }

    @AfterMethod
    public void endTest() {
        softAssert = null;
        userService.delete(user1.getId());
        user1 = null;
    }

    @Test
    public void findAll() {
        List<UserDTO> usuarios = userService.findAll();

        softAssert.assertNotNull(usuarios, "T01 - NotNull:");
        softAssert.assertEquals(usuarios.size(), 1, "T02 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void findOne() {
        Long id = user1.getId();

        UserDTO found = userService.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), user1.getName(), "T03 - Equals:");
        softAssert.assertEquals(found.getLastName(), user1.getLastName(), "T04 - Equals:");
        softAssert.assertEquals(found.getEmail(), user1.getEmail(), "T05 - Equals:");

        softAssert.assertEquals(found.getActive(), user1.getActive(), "T07 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void findUserByEmail() {
        String email = user1.getEmail();

        UserDTO found = userService.findUserByEmail(email);

        softAssert.assertNotNull(found, "T01 - NotNull:");

        softAssert.assertEquals(found.getName(), user1.getName(), "T03 - Equals:");
        softAssert.assertEquals(found.getLastName(), user1.getLastName(), "T04 - Equals:");
        softAssert.assertEquals(found.getEmail(), user1.getEmail(), "T05 - Equals:");
        softAssert.assertEquals(found.getActive(), user1.getActive(), "T07 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void save() {
        UserDTO user2 = new UserDTO("Zé", "Brasil", "zebrasil@gmail.com", "345", 1);

        userService.save(user2);

        Long id2 = user2.getId();

        UserDTO found = userService.findOne(id2);
        List<UserDTO> usuarios = userService.findAll();

        softAssert.assertNotNull(id2, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");

        softAssert.assertNotNull(usuarios, "T03 - NotNull:");
        softAssert.assertEquals(usuarios.size(), 2, "T04 - Equals:");

        softAssert.assertEquals(found.getName(), user2.getName(), "T05 - Equals:");
        softAssert.assertEquals(found.getLastName(), user2.getLastName(), "T06 - Equals:");
        softAssert.assertEquals(found.getEmail(), user2.getEmail(), "T07 - Equals:");

        softAssert.assertEquals(found.getActive(), user2.getActive(), "T08 - Equals:");

        userService.delete(id2);

        softAssert.assertAll();
    }

    @Test
    public void update() {
        Long id = user1.getId();

        UserDTO update = userService.findOne(id);

        update.setName("Marcos");
        update.setLastName("Morais");
        update.setEmail("marcosmorais@gmail.com");
        // Simular o não preenchimento da senha
        update.setPassword("");

        userService.edit(update);

        UserDTO found = userService.findOne(id);

        softAssert.assertNotNull(found, "T01 - NotNull:");
        softAssert.assertEquals(found.getName(), "Marcos", "T02 - Equals:");
        softAssert.assertNotEquals(found.getName(), "Taciano", "T03 - Equals:");
        softAssert.assertEquals(found.getLastName(), "Morais", "T04 - Equals:");
        softAssert.assertNotEquals(found.getLastName(), "Silva", "T05 - Equals:");
        softAssert.assertEquals(found.getEmail(), "marcosmorais@gmail.com", "T06 - Equals:");
        softAssert.assertEquals(found.getActive(), 1, "T07 - Equals:");

        softAssert.assertEquals(found.getPassword(), user1.getPassword(), "T08 - Equals:");

        update = userService.findOne(id);
        // Simular o não preenchimento da senha com null
        update.setPassword(null);
        userService.edit(update);

        found = userService.findOne(id);
        softAssert.assertEquals(found.getPassword(), user1.getPassword(), "T09 - Equals:");

        UserDTO old = userService.findOne(id);
        String oldPassword = old.getPassword();
        // Simular o preenchimento da senha
        old.setPassword("123456");

        userService.edit(old);

        UserDTO found2 = userService.findOne(id);

        softAssert.assertNotNull(found2, "T10 - NotNull:");
        softAssert.assertEquals(found2.getName(), "Marcos", "T11 - Equals:");
        softAssert.assertEquals(found2.getLastName(), "Morais", "T12 - Equals:");
        softAssert.assertEquals(found2.getEmail(), "marcosmorais@gmail.com", "T13 - Equals:");
        softAssert.assertEquals(found2.getActive(), 1, "T14 - Equals:");

        softAssert.assertNotEquals(found2.getPassword(), oldPassword, "T15 - Equals:");

        softAssert.assertAll();
    }

    @Test
    public void delete() {
        User user3 = new User();

        user3.setName("Test Project");
        user3.setLastName("TestNG Project");
        user3.setEmail("test@gmail.com");
        user3.setPassword("123");
        user3.setActive(1);

        userService.save(user3.convertToDTO());

        UserDTO found = userService.findUserByEmail(user3.getEmail());
        List<UserDTO> usuarios = userService.findAll();

        softAssert.assertNotNull(found, "T02 - NotNull:");
        softAssert.assertNotNull(found.getId(), "T01 - NotNull:");

        softAssert.assertNotNull(usuarios, "T03 - NotNull:");
        softAssert.assertEquals(usuarios.size(), 2, "T04 - Equals:");

        userService.delete(found.getId());

        found = userService.findOne(found.getId());
        usuarios = userService.findAll();

        softAssert.assertNull(found, "T05 - NotNull:");

        softAssert.assertNotNull(usuarios, "T06 - NotNull:");
        softAssert.assertEquals(usuarios.size(), 1, "T07 - Equals:");

        softAssert.assertAll();
    }
}
