package br.ufrn.dct.apf.model;

import java.io.Serializable;

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
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_story_id", nullable = false)
    private UserStory userStory;

    public DataFunction() {
    }
    
    public DataFunction(String name, String type) {
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
}
