package br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth;

import br.ufpr.tads.msbantadsauth.Domain.Events.Common.DomainEvent;

public class ClientPasswordFailEvent extends DomainEvent {
    private String cpf;

    public ClientPasswordFailEvent(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    
}
