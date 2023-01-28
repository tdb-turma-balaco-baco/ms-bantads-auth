package br.ufpr.tads.msbantadsauth.Infrastructure.Services.Security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufpr.tads.msbantadsauth.Application.Abstractions.Security.IPasswordEncryption;

@Service
public class PasswordEncryption implements IPasswordEncryption{

    @Override
    public String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(password);
        return encryptedPassword;
    }
    
}
