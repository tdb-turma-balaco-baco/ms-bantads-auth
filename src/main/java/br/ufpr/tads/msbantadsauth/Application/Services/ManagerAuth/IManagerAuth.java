package br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth;

import br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth.Events.CreateManagerAuthEvent;

public interface IManagerAuth {
    void createManagerAuth(CreateManagerAuthEvent event);
}
