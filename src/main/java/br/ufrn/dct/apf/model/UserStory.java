package br.ufrn.dct.apf.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "user_story")
public class UserStory implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_story_id")
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "*Especifique um nome para o user story")
    private String name;

    @Column(name = "description")
    @NotEmpty(message = "*Escreva uma descrição para o user story")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "userStory", targetEntity = DataFunction.class, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Set<DataFunction> dataFunction = new HashSet<>();

    public UserStory() {
    }

    public UserStory(String name, String description) {
        this.name = name;
        this.description = description;
    }

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

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<DataFunction> getDataFunction() {
        return dataFunction;
    }

    public void setDataFunction(Set<DataFunction> datas) {
        this.dataFunction = datas;
    }

    public void addData(DataFunction data) {
        if (data != null) {
            data.setUserStory(this);
        }

        if (!dataFunction.contains(data)) {
            dataFunction.add(data);
        }
    }
}