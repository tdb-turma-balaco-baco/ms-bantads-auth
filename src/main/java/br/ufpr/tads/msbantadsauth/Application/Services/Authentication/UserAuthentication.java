package br.ufpr.tads.msbantadsauth.Application.Services.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpr.tads.msbantadsauth.Application.Abstractions.Security.IPasswordManager;
import br.ufpr.tads.msbantadsauth.Domain.Entities.User;
import br.ufpr.tads.msbantadsauth.Infrastructure.Persistence.UserRepository;

@Service
public class UserAuthentication implements IUserAuthentication {

    @Autowired
    IPasswordManager _passwordManager;

    @Autowired
    UserRepository _userRepository;
    
    @Override
    public boolean login(String email, String password) {

        User user = _userRepository.findUserByLogin(email);
        if(user == null) return false;

        boolean validLogin = _passwordManager.verifyPassword(password, user.getPassword());
        return validLogin;
    }
    
}
