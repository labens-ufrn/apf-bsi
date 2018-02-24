package br.ufrn.dct.apf.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Especifique um nome para o projeto")
    private String name;

    @Column(name = "description")
    @NotEmpty(message = "Escreva uma descrição para o projeto")
    private String description;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@NotEmpty(message = "Data é uma informação obrigatória.")
    private Date created;

    @Column(name = "active")
    private int active;
    
    @OneToMany(mappedBy = "project", targetEntity = Member.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Member> team = new HashSet<>();
    
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserStory> userStories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Set<Member> getTeam() {
        return team;
    }

    public void setTeam(Set<Member> team) {
        this.team = team;
    }
    
    public void setOwner(Member owner) {
        
        if(team == null) {
            team = new HashSet<>(0);
        }
        
        if (!team.contains(owner)) {
            team.add(owner);
        }
    }
}
