package br.ufrn.dct.apf.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.persistence.JoinColumn;

import br.ufrn.dct.apf.dto.UserDTO;
import br.ufrn.dct.apf.interfaces.IEntity;
import br.ufrn.dct.apf.utils.Mapper;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "user")
public class User implements IEntity {

    /**
     * Serial number.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email")
    @Email(message = "Email inválido!")
    @NotEmpty(message = "Digite seu email!")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "Sua senha precisa ser acima de 5 caracteres")
    @NotEmpty(message = "Digite sua senha")
    @Transient
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "Digite seu nome")
    private String name;

    @Column(name = "last_name")
    @NotEmpty(message = "Digite seu sobrenome")
    private String lastName;

    @Column(name = "active")
    private int active;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )

    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setNewRole(Role role) {

        if(roles == null) {
            roles = new HashSet<>(0);
        }

        if (!roles.contains(role)) {
            roles.add(role);
        }
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
        User other = (User) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    @Override
    public String toString() {
        return "User@" + this.id + "[" + this.name + "]";
    }

    @Override
    public UserDTO convertToDTO() {
        return Mapper.EntityToDTO(this, UserDTO.class);
    }
}
