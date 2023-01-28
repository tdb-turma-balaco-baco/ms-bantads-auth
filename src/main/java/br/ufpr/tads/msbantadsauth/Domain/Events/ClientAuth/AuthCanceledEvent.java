package br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth;

import br.ufpr.tads.msbantadsauth.Domain.Events.Common.DomainEvent;

public class AuthCanceledEvent extends DomainEvent{
    private String cpf;
    private String email;
    
    public AuthCanceledEvent(String cpf, String email) {
        this.cpf = cpf;
        this.email = email;
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

    
}
