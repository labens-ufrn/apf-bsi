package br.ufrn.dct.apf.service;

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
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.repository.RoleRepository;

@ContextConfiguration("/spring-test-beans.xml")
@DataJpaTest
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    private SoftAssert softAssert;
    
    @Autowired
    private RoleRepository roleService;

    @Autowired
    private UserService service;
    
    private User user1;
    
    private Role ROLE_USER;

    @BeforeMethod
    public void startTest() {
        softAssert = new SoftAssert();
        
        ROLE_USER = new Role();
        ROLE_USER.setRole("USER");
        
        roleService.save(ROLE_USER);
        
        user1 = new User();
        
        user1.setName("Taciano Silva");
        user1.setLastName("Silva");
        user1.setEmail("tacianosilva@gmail.com");
        user1.setPassword("12345");
        user1.setActive(1);
        
        service.save(user1);
    }
    
    @AfterMethod
    public void endTest() {
        softAssert = null;
        service.delete(user1.getId());
        roleService.delete(ROLE_USER.getId());
        user1 = null;
    }

    @Test
    public void findAll() {
        List<User> usuarios = service.findAll();
        
        softAssert.assertNotNull(usuarios, "T01 - NotNull:");
        softAssert.assertEquals(usuarios.size(), 1, "T02 - Equals:");
        
        softAssert.assertAll();
    }

    @Test
    public void findOne() {
        Long id = user1.getId();
        
        User found = service.findOne(id);
        
        softAssert.assertNotNull(found, "T01 - NotNull:");
        
        softAssert.assertEquals(found.getName(), user1.getName(), "T03 - Equals:");
        softAssert.assertEquals(found.getLastName(), user1.getLastName(), "T04 - Equals:");
        softAssert.assertEquals(found.getEmail(), user1.getEmail(), "T05 - Equals:");
        
        softAssert.assertNotNull(found.getActive(), "T06 - NotNull:");
        softAssert.assertEquals(found.getActive(), user1.getActive(), "T07 - Equals:");
        
        softAssert.assertAll();
    }

    @Test
    public void save() {
        User user2 = new User();
        
        user2.setName("Zé");
        user2.setLastName("Brasil");
        user2.setEmail("zebrasil@gmail.com");
        user2.setPassword("345");
        user2.setActive(1);
        
        service.save(user2);
        
        Long id2 = user2.getId();
        
        User found = service.findOne(id2);
        List<User> usuarios = service.findAll();
        
        softAssert.assertNotNull(id2, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");
        
        softAssert.assertNotNull(usuarios, "T03 - NotNull:");
        softAssert.assertEquals(usuarios.size(), 2, "T04 - Equals:");
        
        softAssert.assertEquals(found.getName(), user2.getName(), "T05 - Equals:");
        softAssert.assertEquals(found.getLastName(), user2.getLastName(), "T06 - Equals:");
        softAssert.assertEquals(found.getEmail(), user2.getEmail(), "T07 - Equals:");
        
        softAssert.assertNotNull(found.getActive(), "T08 - NotNull:");
        softAssert.assertEquals(found.getActive(), user2.getActive(), "T08 - Equals:");
        
        service.delete(id2);
        
        softAssert.assertAll();
    }
    
    @Test
    public void update() {
        Long id = user1.getId();
        
        User update = service.findOne(id);

        update.setName("Marcos");
        update.setLastName("Morais");
        update.setEmail("marcosmorais@gmail.com");
        
        service.save(update);
        
        User found = service.findOne(id);
        
        softAssert.assertNotNull(found, "T01 - NotNull:");
        
        softAssert.assertEquals(found.getName(), "Marcos", "T02 - Equals:");
        softAssert.assertNotEquals(found.getName(), "Taciano", "T03 - Equals:");
        softAssert.assertEquals(found.getLastName(), "Morais", "T04 - Equals:");
        softAssert.assertNotEquals(found.getLastName(), "Silva", "T05 - Equals:");
        
        softAssert.assertEquals(found.getEmail(), "marcosmorais@gmail.com", "T03 - Equals:");
        softAssert.assertEquals(found.getActive(), 1, "T04 - Equals:");

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
        
        service.save(user3);
        
        Long id3 = user3.getId();
        
        User found = service.findOne(id3);
        List<User> usuarios = service.findAll();
        
        softAssert.assertNotNull(id3, "T01 - NotNull:");
        softAssert.assertNotNull(found, "T02 - NotNull:");
        
        softAssert.assertNotNull(usuarios, "T03 - NotNull:");
        softAssert.assertEquals(usuarios.size(), 2, "T04 - Equals:");
        
        service.delete(id3);
        
        found = service.findOne(id3);
        usuarios = service.findAll();
        
        softAssert.assertNull(found, "T05 - NotNull:");
        
        softAssert.assertNotNull(usuarios, "T06 - NotNull:");
        softAssert.assertEquals(usuarios.size(), 1, "T07 - Equals:");
        
        softAssert.assertAll();
    }
}
