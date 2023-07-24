package br.tads.ufpr.services.impl;

import br.tads.ufpr.dto.UserLoginResponse;
import br.tads.ufpr.model.User;
import br.tads.ufpr.model.UserRepository;
import br.tads.ufpr.services.AuthenticationService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;

import java.util.NoSuchElementException;
import java.util.Optional;

@ApplicationScoped
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    UserRepository userRepository;

    @Override
    public UserLoginResponse login(String email, String password) {
        String sanitazedEmail = email.toLowerCase().trim();

        Log.info("Credencial VALIDANDO:" + sanitazedEmail);
        Optional<User> optional = userRepository.findByEmail(sanitazedEmail);

        if (optional.isPresent()) {
            User user = optional.get();

            if (BcryptUtil.matches(password, user.password)) {
                UserLoginResponse dto = new UserLoginResponse(user.id, user.email, user.userType);
                Log.info("Credencial OK:" + dto);

                return dto;
            }
        }

        Log.warn("Credencial FALHOU:" + email);
        throw new NoSuchElementException();
    }
}
