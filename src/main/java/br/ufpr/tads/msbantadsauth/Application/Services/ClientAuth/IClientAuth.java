package br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth;

import br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth.Events.CreateClientAuthEvent;
import br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth.Events.GeneratePasswordEvent;

public interface IClientAuth {
    void createClientAuth(CreateClientAuthEvent event);

    void generatePassword(GeneratePasswordEvent event);
}
