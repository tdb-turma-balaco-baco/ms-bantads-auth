package br.ufpr.tads.msbantadsauth.Application.Services.Authentication;

import br.ufpr.tads.msbantadsauth.Application.Services.Authentication.Result.UserLogin;

public interface IUserAuthentication {
    
    UserLogin login(String email, String password);
}
