package br.ufpr.tads.msbantadsauth.Application.Abstractions.Security;

public interface IPasswordManager {
    String encryptPassword(String password);
    boolean verifyPassword(String plainPassword, String encryptedPassword);
    String generatePassword();
}
