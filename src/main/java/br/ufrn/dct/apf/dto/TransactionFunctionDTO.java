package br.ufrn.dct.apf.dto;

import java.io.Serializable;

import br.ufrn.dct.apf.model.Project;
import br.ufrn.dct.apf.model.UserStory;

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
public class TransactionFunctionDTO implements Serializable {

    /**
     * Serial Id.
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String type;

    private Integer fileTypeReferenced;

    private Integer dataElementTypes;
    
    private String description;

    private UserStory userStory;
    
    private Project project;
    
    public TransactionFunctionDTO() {}

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
}
