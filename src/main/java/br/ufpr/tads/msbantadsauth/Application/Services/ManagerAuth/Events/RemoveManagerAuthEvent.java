package br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth.Events;

public class RemoveManagerAuthEvent {
    private String cpf;

    public RemoveManagerAuthEvent() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    
}
