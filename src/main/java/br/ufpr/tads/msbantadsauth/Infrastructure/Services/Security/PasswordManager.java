package br.ufpr.tads.msbantadsauth.Infrastructure.Services.Security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufpr.tads.msbantadsauth.Application.Abstractions.Security.IPasswordManager;

@Service
public class PasswordManager implements IPasswordManager{

    @Override
    public String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(password);
        return encryptedPassword;
    }

    @Override
    public String generatePassword() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
