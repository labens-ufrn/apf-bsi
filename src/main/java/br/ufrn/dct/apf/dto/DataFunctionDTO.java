package br.ufrn.dct.apf.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.UserStory;

/**
 * 
 * @author Taciano Morais Silva
 * @since 04/04/2019
 */
public class DataFunctionDTO implements Serializable {

    /**
     * Serial Id.
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String type;

    /**
     * number of Record Element Types (RET).
     */
    private Long recordElementTypes;

    /**
     * number of Data Element Types (DET).
     */
    private Long dataElementTypes;
    
    private Project project;

    private UserStory userStory;
    
    private Set<UserStory> userStories = new HashSet<>();
    
    /**
     * Internal Logical Files (ILF's, in Portuguese, Arquivos Lógico Interno - ALI's).
     *
     * A user identifiable group of logically related data that resides entirely
     * within the applications boundary and is maintained through external inputs.
     * 
     * External Interface Files (EIF’s, in Portuguese, Arquivos Interface Externa - AIE's).
     *
     * A user identifiable group of logically related data that is used for reference
     * purposes only. The data resides entirely outside the application and is
     * maintained by another application.
     *
     * The external interface file is an internal logical file for another application.
     *
     * Source: <a href="http://www.softwaremetrics.com/fpafund.htm">http://www.softwaremetrics.com/fpafund.htm</a>
     */
    public DataFunctionDTO() {
    }
    
    /**
     * 
     * @param name A data function's name.
     * @param type
     */
    public DataFunctionDTO(String name, String type) {
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
    
    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }
}
