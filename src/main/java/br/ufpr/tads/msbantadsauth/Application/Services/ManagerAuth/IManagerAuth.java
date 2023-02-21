package br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth;

import br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth.Events.CreateManagerAuthEvent;
import br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth.Events.RemoveManagerAuthEvent;

public interface IManagerAuth {
    void createManagerAuth(CreateManagerAuthEvent event);
    void removeManagerAuth(RemoveManagerAuthEvent event);
}
