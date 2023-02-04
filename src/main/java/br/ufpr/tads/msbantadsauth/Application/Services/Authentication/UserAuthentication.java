package br.ufpr.tads.msbantadsauth.Application.Services.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpr.tads.msbantadsauth.Application.Abstractions.Security.IPasswordManager;

@Service
public class UserAuthentication implements IUserAuthentication {

    @Autowired
    IPasswordManager _passwordManager;
    
    @Override
    public boolean login(String email, String password) {

        String encryptedPassword = _passwordManager.encryptPassword(password);

        //Try to verify if user exists in database with same email and password sent.
        //and whether user isn't blocked.
        boolean userExists = true; // chama funcao repository

        return userExists;
    }
    
}
