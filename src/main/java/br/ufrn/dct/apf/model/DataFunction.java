package br.ufrn.dct.apf.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "data_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    /**
     * number of Record Element Types (RET).
     */
    @Column(name = "ret")
    private Long recordElementTypes;

    /**
     * number of Data Element Types (DET).
     */
    @Column(name = "det")
    private Long dataElementTypes;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_story_id", nullable = false)
    private UserStory userStory;
    
    /**
     * Internal Logical Files (ILF's, in Portuguese, Arquivos Lógico Interno - ALI's).
     *
     * A user identifiable group of logically related data that resides entirely
     * within the applications boundary and is maintained through external inputs.
     *
     * Source: <a href="http://www.softwaremetrics.com/fpafund.htm">http://www.softwaremetrics.com/fpafund.htm</a>
     *
     * @author Taciano Morais Silva
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
     * @author Taciano Morais Silva
     * @since 04/04/2019
     * @param name A data function's name.
     */
    public static DataFunction createEIF(String name) {
        return new DataFunction(name, TYPE_EIF);
    }
    
    private DataFunction() {}

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
}
