package br.ufpr.tads.msbantadsauth.Application.Services.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpr.tads.msbantadsauth.Application.Abstractions.Security.IPasswordEncryption;

@Service
public class UserAuthentication implements IUserAuthentication {

    @Autowired
    IPasswordEncryption _passwordEncryption;
    
    @Override
    public boolean login(String email, String password) {

        String encryptedPassword = _passwordEncryption.encryptPassword(password);

        //Tenta verificar no banco se existe usuário com email e senha passados
        boolean userExists = true; // chama funcao

        return userExists;
    }
    
}
