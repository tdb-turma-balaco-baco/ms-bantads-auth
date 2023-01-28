package br.ufpr.tads.msbantadsauth.Application.Services.Authentication;

public interface IUserAuthentication {
    
    boolean login(String email, String password);
}
