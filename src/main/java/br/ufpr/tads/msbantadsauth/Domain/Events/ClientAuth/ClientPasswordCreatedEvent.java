package br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth;

import br.ufpr.tads.msbantadsauth.Domain.Events.Common.DomainEvent;

public class ClientPasswordCreatedEvent extends DomainEvent {
    private String cpf;
    private String email;
    private String password;

    public ClientPasswordCreatedEvent() {
    }
    
    public ClientPasswordCreatedEvent(String cpf, String email, String password) {
        this.cpf = cpf;
        this.email = email;
        this.password = password;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    
}
