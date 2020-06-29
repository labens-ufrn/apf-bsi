package br.ufrn.dct.apf;

import java.util.GregorianCalendar;
import java.util.HashSet;

import br.ufrn.dct.apf.model.Member;
import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.User;

public class App {

    public static void main(String[] args) {

        HashSet<String> celebrities = new HashSet<String>();

        celebrities.add("Tom Cruise");
        celebrities.add("Akshay Kumar");
        celebrities.add("Will Smith");
        celebrities.add("Jackie Chan");
        celebrities.add("Will Smith");

        System.out.println("Does set contains Tom Cruise ? :- " + celebrities.contains("Tom Cruise"));

        System.out.println("Does set contains Johnny Depp ? :- " + celebrities.contains("Johnny Depp"));

        User analista = new User();
        analista.setId(1L);
        analista.setName("Taciano");
        analista.setLastName("Morais Silva");
        analista.setEmail("tacianosilva@gmai.com");
        analista.setPassword("12345");

        User desenvolvedor = new User();
        desenvolvedor.setId(2L);
        desenvolvedor.setName("Zé");
        desenvolvedor.setLastName("Silva");
        desenvolvedor.setEmail("zesilva@gmai.com");
        desenvolvedor.setPassword("12345");

        Project p1 = new Project();

        p1.setId(1L);
        p1.setName("APF Project");
        p1.setDescription("Analisador de Pontos por Função");
        p1.setCreatedOn(GregorianCalendar.getInstance().getTime());

        HashSet<Member> team = new HashSet<Member>();

        Member m1 = new Member();
        m1.setId(1L);
        m1.setUser(analista);
        m1.setProject(p1);
        m1.setCreatedOn(GregorianCalendar.getInstance().getTime());

        Member m2 = new Member();
        m2.setId(2L);
        m2.setUser(desenvolvedor);
        m2.setProject(p1);
        m2.setCreatedOn(GregorianCalendar.getInstance().getTime());

        team.add(m1);

        System.out.println("Does set contains m1 ? :- " + team.contains(m1));
        System.out.println("Does set contains m2 ? :- " + team.contains(m2));
    }
}
