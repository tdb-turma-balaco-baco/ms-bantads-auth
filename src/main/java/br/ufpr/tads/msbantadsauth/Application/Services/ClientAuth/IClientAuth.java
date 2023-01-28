package br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth;

import br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth.Events.CreateClientAuthEvent;

public interface IClientAuth {
    void createClientAuth(CreateClientAuthEvent event);
}
