package br.ufrn.dct.apf;

import br.ufrn.dct.apf.model.Projeto;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.model.UserStory;

public class ProvaDeConceito {

    public static void main(String[] args) {

        User proprietario = new User();

        Projeto p1 = new Projeto();

        p1.setNome("Sistema APF");
        p1.setDescricao("Sistema para auxilio na contagem por Pontos de Função");
        //p1.setProprietario(proprietario);

        //Equipe e1 = p1.getEquipe();

        UserStory us1 = new UserStory("US01", "Manter Projeto");
        UserStory us2 = new UserStory("US02", "Manter UserStory");

    }
}
