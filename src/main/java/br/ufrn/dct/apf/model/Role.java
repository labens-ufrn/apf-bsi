package br.ufrn.dct.apf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Regra para definir permissões padrão de usuários do sistema.
     */
    public static final String USER_ROLE = "USER";

    public static final String ADMIN_ROLE = "ADMIN";

    public static final String PROJECT_MANAGER_ROLE = "PROJECT MANAGER";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int id;

    @Column(name = "role_name")
    private String roleName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String toString() {
        return this.roleName;
    }
}
