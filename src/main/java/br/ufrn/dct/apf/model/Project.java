package br.ufrn.dct.apf.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The Project class represents Software Design objects with their basic elements:
 *  name, description, user stories list, data function list (database),
 *  and list of transaction functions (requirements).
 * We follow an agile methodology based on XP and YP (easYProcess).
 * @author Taciano Morais Silva
 * @version 1.0
 * @since 17/10/2017, 22h51m
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {

    /**
     * Serial number.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "name", unique = true)
    @NotEmpty(message = "Especifique um nome para o projeto")
    private String name;

    @Column(name = "description", nullable = false)
    @NotEmpty(message = "Escreva uma descrição para o projeto")
    private String description;

    @Column(name = "created_on", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdOn;

    @Column(name = "isPrivate", columnDefinition = "boolean default false")
    @NotNull
    private Boolean isPrivate;

    @Column(name = "active")
    private int active;

    @OneToMany(mappedBy = "project", targetEntity = Member.class, orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Member> team = new HashSet<>();

    @OneToMany(mappedBy = "project", targetEntity = UserStory.class, orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserStory> userStories = new HashSet<>();

    @OneToMany(mappedBy = "project", targetEntity = DataFunction.class, orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<DataFunction> dataFunctions = new HashSet<>();

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

    public Set<DataFunction> getDataFunctions() {
        return dataFunctions;
    }

    public void setDataFunctions(Set<DataFunction> dataFunctions) {
        this.dataFunctions = dataFunctions;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }
}
