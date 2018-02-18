package br.ufrn.dct.apf.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "function")
public class Function implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "function_id")
    private Long id;

    @OneToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name="user_id", unique= true, nullable=false, insertable=true, updatable=true)
    private User user;

    @OneToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name="role_id", unique= true, nullable=false, insertable=true, updatable=true)
    private Role role;
    
    @ManyToOne
    @JoinColumn(name="team_id", nullable=false)
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
