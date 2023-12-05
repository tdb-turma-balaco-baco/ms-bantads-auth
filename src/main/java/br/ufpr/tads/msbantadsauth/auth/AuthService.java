package br.ufpr.tads.msbantadsauth.auth;

import br.ufpr.tads.msbantadsauth.inbound.CreateUser;
import br.ufpr.tads.msbantadsauth.inbound.LoginRequest;
import br.ufpr.tads.msbantadsauth.inbound.UpdateUser;
import br.ufpr.tads.msbantadsauth.outbound.LoginResponse;
import br.ufpr.tads.msbantadsauth.outbound.UserResponse;
import br.ufpr.tads.msbantadsauth.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordManager passwordManager;
    private final JwtService jwtService;

    public LoginResponse login(@Valid @NonNull LoginRequest request) {
        log.debug("[retrieving] login '{}'", request.email());
        try {
            var user = this.userService.findUserByEmail(request.email());

            if (passwordManager.matches(request.password(), user.getPassword())) {
                log.info("[retrieving] generating jwt for '{}'", request.email());
                String jwt = this.jwtService.generateJwt(user);
                return new LoginResponse(jwt);
            }

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Transactional
    public UserResponse create(@Valid @NonNull CreateUser request) {
        log.debug("[creating] creating {}: '{}'", request.profileRole(), request.email());

        String generatedRandomPassword = this.passwordManager.generateRandomPassword();
        var createWithPassword = new CreateUser(request.email(), request.profileRole(), generatedRandomPassword);

        return this.userService.create(createWithPassword);
    }

    @Transactional
    public UserResponse update(@Valid @NonNull UpdateUser request) {
        log.debug("[updating] '{}'", request.userId());
        UpdateUser updateUser = request;

        if (Objects.nonNull(request.password())) {
            log.debug("[updating] encoding user '{}' updated password", request.userId());
            var encodedPassword = this.passwordManager.encode(request.password());
            updateUser = new UpdateUser(request.userId(), request.email(), encodedPassword);
        }

        return this.userService.update(updateUser);
    }
}
