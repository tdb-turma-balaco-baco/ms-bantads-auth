package br.ufpr.tads.msbantadsauth.Domain.Events.ManagerAuth;

import br.ufpr.tads.msbantadsauth.Domain.Events.Common.DomainEvent;

public class RemovedManagerAccessEvent extends DomainEvent {
    private String cpf;

    public RemovedManagerAccessEvent(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    
}
