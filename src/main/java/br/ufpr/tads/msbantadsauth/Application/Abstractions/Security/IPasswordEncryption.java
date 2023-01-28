package br.ufpr.tads.msbantadsauth.Application.Abstractions.Security;

public interface IPasswordEncryption {
    String encryptPassword(String password);
}
