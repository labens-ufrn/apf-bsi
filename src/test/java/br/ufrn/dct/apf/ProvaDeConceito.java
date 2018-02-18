package br.ufrn.dct.apf;

import java.util.Set;
import java.util.TreeSet;

import br.ufrn.dct.apf.model.Function;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.Team;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.service.UserService;
import br.ufrn.dct.apf.service.UserService;

public class ProvaDeConceito {

    public static void main(String[] args) {
        
        System.out.println("Criando usuário ...");
        
        User analista = new User();
        analista.setName("Taciano Morais Silva");
        analista.setEmail("tacianosilva@gmai.com");
        analista.setPassword("12345");
        
        UserService userService = new UserService();
        
        userService.saveUser(analista);
        
        System.out.println("Usuário salvo!");
        
        Role projectOwner = new Role();
        projectOwner.setRole("Project Owner");

        Project p1 = new Project();

        p1.setName("Sistema APF");
        p1.setDescription("Sistema para auxilio na contagem por Pontos de Função");
        //p1.setProprietario(proprietario);

        Team t1 = new Team(p1);
        
        Function f1 = new Function();
        f1.setUser(analista);
        f1.setRole(projectOwner);
        
        Set<Function> functions = new TreeSet<>();
        functions.add(f1);
        
        t1.setFunctions(functions);

        UserStory us1 = new UserStory("US01", "Manter Projeto");
        UserStory us2 = new UserStory("US02", "Manter UserStory");

    }
}
