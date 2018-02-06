package br.ufrn.dct.apf;

import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.model.UserStory;
import br.ufrn.dct.apf.service.UserService;
import br.ufrn.dct.apf.service.UserServiceImpl;

public class ProvaDeConceito {

    public static void main(String[] args) {
        
        System.out.println("Criando usuário ...");
        
        User analista = new User();
        analista.setName("Taciano Morais Silva");
        analista.setEmail("tacianosilva@gmai.com");
        analista.setPassword("12345");
        
        UserService userService = new UserServiceImpl();
        
        userService.saveUser(analista);
        
        System.out.println("Usuário salvo!");

        Project p1 = new Project();

        p1.setName("Sistema APF");
        p1.setDescription("Sistema para auxilio na contagem por Pontos de Função");
        //p1.setProprietario(proprietario);

        //Equipe e1 = p1.getEquipe();

        UserStory us1 = new UserStory("US01", "Manter Projeto");
        UserStory us2 = new UserStory("US02", "Manter UserStory");

    }
}
