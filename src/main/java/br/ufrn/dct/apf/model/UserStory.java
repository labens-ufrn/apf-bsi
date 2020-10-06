package br.ufrn.dct.apf.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "user_story")
public class UserStory implements Serializable {

    /**
     * Serial number.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_story_id")
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "*Especifique um nome para o user story")
    private String name;

    @Column(name = "description")
    @NotEmpty(message = "*Escreva uma descrição para o user story")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "userStory", targetEntity = DataFunction.class, fetch = FetchType.EAGER)
    private Set<DataFunction> dataFunctions = new HashSet<>();
    
    @OneToMany(mappedBy = "userStory", targetEntity = TransactionFunction.class, fetch = FetchType.EAGER)
    private Set<TransactionFunction> transactions = new HashSet<>();

    public UserStory() {
    }

    public UserStory(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<DataFunction> getDataFunctions() {
        return dataFunctions;
    }

    public void setDataFunctions(Set<DataFunction> datas) {
        this.dataFunctions = datas;
    }

    public void addData(DataFunction data) {
        if (data != null) {
            data.setUserStory(this);
        }

        if (!dataFunctions.contains(data)) {
            dataFunctions.add(data);
        }
    }
    
    public Set<TransactionFunction> getTransactionFunctions() {
        return transactions;
    }
    
    public void setTransactionFunctions(Set<TransactionFunction> transactions) {
        this.transactions = transactions;
    }
    
    public void addTransaction(TransactionFunction transaction) {
        if (transaction != null) {
            transaction.setUserStory(this);
        }

        if (!transactions.contains(transaction)) {
            transactions.add(transaction);
        }
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
        UserStory other = (UserStory) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }
}
