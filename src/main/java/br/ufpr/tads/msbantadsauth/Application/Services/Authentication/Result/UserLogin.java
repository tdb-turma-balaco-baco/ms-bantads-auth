package br.ufpr.tads.msbantadsauth.Application.Services.Authentication.Result;

public class UserLogin {
    private String cpf;
    private String type;
    private String name;
    
    public UserLogin(String cpf, String type, String name) {
        this.cpf = cpf;
        this.type = type;
        this.name = name;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
}
