package br.ufrn.dct.apf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Transaction Functions
 *
 * There are three types of transaction functions.
 *
 *  External Inputs
 *  External Outputs
 *  External Inquiries
 *
 * Transaction functions are made up of the processes that are exchanged
 * between the user, the external applications and the application being measured.
 *
 * https://www.tutorialspoint.com/estimation_techniques/estimation_techniques_function_points.htm
 *
 * @author Taciano Morais Silva
 * @version 1.0
 * @since 23/05/2019, 14h11m
 *
 */
@Entity
@Table(name = "transactionFunction")
public class TransactionFunction implements Serializable {

    /**
     * Serial Id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * External Input (EI) is a transaction function in which Data goes “into”
     * the application from outside the boundary to inside. This data is coming
     * external to the application.
     */
    public static final String TYPE_EI = "TYPE_EI";

    /**
     * External Output (EO) is a transaction function in which data comes “out”
     * of the system. Additionally, an EO may update an ILF. The data creates reports
     * or output files sent to other applications.
     */
    public static final String TYPE_EO = "TYPE_EO";

    /**
     * External Inquiry (EQ) is a transaction function
     * with both input and output components that result in data retrieval.
     */
    public static final String TYPE_EQ = "TYPE_EQ";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trans_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    /**
     * number of File Type Referenced (FTR).
     *
     * File Type Referenced (FTR) is the largest user identifiable subgroup
     * within the EI, EO, or EQ that is referenced to.
     */
    @Column(name = "ftr")
    private Integer fileTypeReferenced;

    /**
     * number of Data Element Types (DET).
     *
     * Data Element Type (DET) is the data subgroup within an FTR.
     * They are unique and user identifiable.
     */
    @Column(name = "det")
    private Integer dataElementTypes;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_story_id")
    private UserStory userStory;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public static TransactionFunction createEI(String name) {
        return new TransactionFunction(name, TYPE_EI);
    }

    public static TransactionFunction createEO(String name) {
        return new TransactionFunction(name, TYPE_EO);
    }

    public static TransactionFunction createEQ(String name) {
        return new TransactionFunction(name, TYPE_EQ);
    }

    public TransactionFunction() {}

    private TransactionFunction(String name, String type) {
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

    public Integer getFileTypeReferenced() {
        return fileTypeReferenced;
    }

    public void setFileTypeReferenced(Integer ftr) {
        this.fileTypeReferenced = ftr;
    }

    public Integer getDataElementTypes() {
        return dataElementTypes;
    }

    public void setDataElementTypes(Integer det) {
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

    public boolean isEI() {
        return getType().equals(TYPE_EI);
    }

    public boolean isEO() {
        return getType().equals(TYPE_EO);
    }

    public boolean isEQ() {
        return getType().equals(TYPE_EQ);
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
        TransactionFunction other = (TransactionFunction) obj;
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
        return "TransactionFunction@" + this.id + "@"+ this.type +"[" + this.name + "]";
    }
}
