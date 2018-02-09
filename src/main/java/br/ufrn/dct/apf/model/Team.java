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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "team", targetEntity = Function.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Function> functions;
    
    public Team() {
        this.functions = new HashSet<>(0);
    }
    
    public Team(Project project) {
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(Set<Function> functions) {
        this.functions = functions;
    }
}
