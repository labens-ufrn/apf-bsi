package br.ufrn.dct.apf.dto;

import br.ufrn.dct.apf.interfaces.IDTO;
import br.ufrn.dct.apf.model.Role;
import br.ufrn.dct.apf.model.User;
import br.ufrn.dct.apf.utils.Mapper;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Set;

public class UserDTO implements IDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String email;

    private String password;

    private String name;

    private String lastName;

    private int active;

    private Set<Role> roles;

    public UserDTO(String name, String lastName, String email, String password, int active) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.active = active;
    }

    public UserDTO() {}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setNewRole(Role userRole) {
        if(roles == null) {
            roles = new HashSet<>(0);
        }

        roles.add(userRole);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User convertToEntity() {
        return Mapper.DTOtoEntity(this, User.class);
    }
}
