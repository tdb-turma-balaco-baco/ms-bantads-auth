package br.ufpr.tads.msbantadsauth.Infrastructure.Services.Security;

import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufpr.tads.msbantadsauth.Application.Abstractions.Security.IPasswordManager;

@Service
public class PasswordManager implements IPasswordManager{

    final int passwordLength = 10;
    final String validCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

    @Override
    public String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(password);
        return encryptedPassword;
    }

    @Override
    public String generatePassword() {
        StringBuilder password = new StringBuilder(passwordLength);
        Random random = new Random();
        for(int x = 0; x < passwordLength; x++){
            char passwordChar = validCharacters.charAt(random.nextInt(validCharacters.length()));
            password.append(passwordChar);
        }
        return password.toString();
    }

    @Override
    public boolean verifyPassword(String plainPassword, String encryptedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(plainPassword, encryptedPassword);
    }
    
}
