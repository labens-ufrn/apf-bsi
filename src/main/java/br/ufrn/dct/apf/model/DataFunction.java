package br.ufrn.dct.apf.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * There are two types of data functions −
 *
 *   Internal Logical Files
 *   External Interface Files
 *
 * Data Functions are made up of internal and external resources
 * that affect the system.
 *
 * @author Taciano Morais Silva
 * @since 04/04/2019
 */
@Entity
@Table(name = "dataFunction")
public class DataFunction implements Serializable {

    /**
     * Serial Id.
     */
    private static final long serialVersionUID = 1L;

    public static final String TYPE_ILF = "TYPE_ILF";

    public static final String TYPE_EIF = "TYPE_EIF";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    /**
     * number of Record Element Types (RET).
     *
     * A Record Element Type (RET) is the largest user identifiable subgroup
     * of elements within an ILF or an EIF. It is best to look at logical
     * groupings of data to help identify them.
     */
    @Column(name = "ret")
    private Long recordElementTypes;

    /**
     * number of Data Element Types (DET).
     *
     * Data Element Type (DET) is the data subgroup within an FTR.
     * They are unique and user identifiable.
     */
    @Column(name = "det")
    private Long dataElementTypes;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_story_id", nullable = true)
    private UserStory userStory;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = true)
    private Project project;

    /**
     * Internal Logical Files (ILF's, in Portuguese, Arquivos Lógico Interno - ALI's).
     *
     * A user identifiable group of logically related data that resides entirely
     * within the applications boundary and is maintained through external inputs.
     *
     * Source: <a href="http://www.softwaremetrics.com/fpafund.htm">http://www.softwaremetrics.com/fpafund.htm</a>
     *
     * @since 04/04/2019
     * @param name A data function's name.
     */
    public static DataFunction createILF(String name) {
        return new DataFunction(name, TYPE_ILF);
    }

    /**
     * External Interface Files (EIF’s, in Portuguese, Arquivos Interface Externa - AIE's).
     *
     * A user identifiable group of logically related data that is used for reference
     * purposes only. The data resides entirely outside the application and is
     * maintained by another application.
     *
     * The external interface file is an internal logical file for another application.
     *
     * Source: <a href="http://www.softwaremetrics.com/fpafund.htm">http://www.softwaremetrics.com/fpafund.htm</a>
     *
     * @since 04/04/2019
     * @param name A data function's name.
     */
    public static DataFunction createEIF(String name) {
        return new DataFunction(name, TYPE_EIF);
    }

    public DataFunction() {}

    private DataFunction(String name, String type) {
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRecordElementTypes() {
        return recordElementTypes;
    }

    public void setRecordElementTypes(Long ret) {
        this.recordElementTypes = ret;
    }

    public Long getDataElementTypes() {
        return dataElementTypes;
    }

    public void setDataElementTypes(Long det) {
        this.dataElementTypes = det;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public UserStory getUserStory() {
        return this.userStory;
    }

    public void setUserStory(UserStory us) {
        this.userStory = us;
    }

    public boolean isILF() {
        return getType().equals(TYPE_ILF);
    }

    public boolean isEIF() {
        return getType().equals(TYPE_EIF);
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
        DataFunction other = (DataFunction) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }

    @Override
    public String toString() {
        return "DataFunction@" + this.id + "@"+ this.type +"[" + this.name + "]";
    }
}
