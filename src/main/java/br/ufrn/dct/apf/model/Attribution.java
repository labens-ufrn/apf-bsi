package br.ufrn.dct.apf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "attribution")
public class Attribution implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Regra para definir permissões membros de um projeto.
     */
    public static final String PROJECT_MANAGER = "PROJECT MANAGER";
    
    public static final String PROJECT_MEMBER = "PROJECT MEMBER";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attrib_id")
    private int id;

    @Column(name = "attrib_name", unique = true)
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String attribName) {
        this.name = attribName;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        Attribution other = (Attribution) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }
}
