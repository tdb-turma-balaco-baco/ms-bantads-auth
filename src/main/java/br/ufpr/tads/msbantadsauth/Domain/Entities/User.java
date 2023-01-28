package br.ufpr.tads.msbantadsauth.Domain.Entities;

import java.util.Date;

public class User {
    private String cpf;
    private String email;
    private String name;
    private String password;
    private Date createdOn;
    private Boolean blocked;
    private Date deletedOn;

    public User(String cpf, String email, String name, String password, Date createdOn, boolean blocked, Date deletedOn) {
        this.cpf = cpf;
        this.email = email;
        this.name = name;
        this.password = password;
        this.createdOn = createdOn;
        this.blocked = blocked;
        this.deletedOn = deletedOn;
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

    
}
