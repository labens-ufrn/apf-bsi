package br.ufrn.dct.apf.count;

import br.ufrn.dct.apf.dto.UserDTO;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.repository.UserRepository;
import br.ufrn.dct.apf.service.AbstractServiceTest;
import br.ufrn.dct.apf.service.BusinessRuleException;
import br.ufrn.dct.apf.service.ProjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "br.ufrn.dct.apf")
public class ContagemTest extends AbstractServiceTest {

    private static final Logger logger = LogManager.getLogger(ContagemTest.class);

    @Autowired
    private ProjectService projectService;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) throws BusinessRuleException {
//        ApplicationContext context
//                = new AnnotationConfigApplicationContext(ContagemTest.class);
//
//        ContagemTest p = context.getBean(ContagemTest.class);
//        p.start(args);

        ConfigurableApplicationContext context = SpringApplication.run(ContagemTest.class, args);
        ContagemTest application = context.getBean(ContagemTest.class);

        application.start(args);
    }

    public void start(String[] args) throws BusinessRuleException {
        IndicativeCount iCount = new IndicativeCount();
        EstimativeCount eCount = new EstimativeCount();

        logger.debug("Debugging log");
        logger.info("Info log");
        logger.warn("Hey, This is a warning!");
        logger.error("Oops! We have an Error. OK");
        logger.fatal("Damn! Fatal error. Please fix me.");

        Project apf = createProjectAPF();

        // Create User Manager
        UserDTO manager = createUser("Taciano","Silva");
        userRepository.save(manager.convertToEntity());

        // Save Project with Manager
        projectService.save(apf, manager);

        // Add User Stories
        addUserStoriesInAPF(apf);

        //Reload APF Project
        apf = projectService.findOne(apf.getId());

        int fp_ic = iCount.calculeFunctionPoint(apf);
        System.out.println("Contagem Indicativa: " + fp_ic + " PF");
        int fp_ec = eCount.calculeFunctionPoint(apf);
        System.out.println("Contagem Estimativa: " + fp_ec + " PF");

        projectService.delete(apf.getId());
        userRepository.delete(manager.convertToEntity());
    }
}
