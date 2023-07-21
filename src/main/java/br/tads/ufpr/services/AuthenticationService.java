package br.tads.ufpr.services;

import br.tads.ufpr.dto.UserLoginResponse;

public interface AuthenticationService {
    UserLoginResponse login(String email, String password);
}
