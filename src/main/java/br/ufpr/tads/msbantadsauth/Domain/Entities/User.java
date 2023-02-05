package br.ufpr.tads.msbantadsauth.Domain.Entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import br.ufpr.tads.msbantadsauth.Domain.Enums.UserType;

@Document("users")
public class User {
    @Id
    private String id;
    private String cpf;
    private String email;
    private String name;
    private String password;
    private Date createdOn;
    private Boolean blocked;
    @Field(targetType = FieldType.STRING)
    private UserType type;
    private Date deletedOn;

    public User(String cpf, String email, String name, String password, Date createdOn, boolean blocked, UserType userType) {
        this.cpf = cpf;
        this.email = email;
        this.name = name;
        this.password = password;
        this.createdOn = createdOn;
        this.blocked = blocked;
        this.type = userType;
    }
    
    public User() {
    }

    public Date getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCPF() {
        return cpf;
    }
    public void setCPF(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getBlocked() {
        return blocked;
    }
    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }
    public Date getDeletedOn() {
        return deletedOn;
    }
    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    
}
