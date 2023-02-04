package br.ufpr.tads.msbantadsauth.Application.Abstractions.Security;

public interface IPasswordManager {
    String encryptPassword(String password);
    String generatePassword();
}
