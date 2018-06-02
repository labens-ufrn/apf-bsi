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

    @Column(name = "created_on", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @NotEmpty(message = "Data é uma informação obrigatória.")
    private Date createdOn;

    @Column(name = "active")
    private int active;

    @OneToMany(mappedBy = "project", targetEntity = Member.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Member> team = new HashSet<Member>();

    @OneToMany(mappedBy = "project", targetEntity = UserStory.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<UserStory> userStories = new HashSet<UserStory>();

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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Set<Member> getTeam() {
        return team;
    }

    public void setTeam(Set<Member> team) {
        this.team = team;
    }

    public void addMember(Member member) {

        if (team == null) {
            team = new HashSet<Member>();
        }

        if (!team.contains(member)) {
            team.add(member);
        }
    }

    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Project other = (Project) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
