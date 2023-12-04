package br.ufpr.tads.msbantadsauth.user;

import br.ufpr.tads.msbantadsauth.auth.JwtService;
import br.ufpr.tads.msbantadsauth.auth.PasswordManager;
import br.ufpr.tads.msbantadsauth.inbound.CreateUser;
import br.ufpr.tads.msbantadsauth.inbound.LoginRequest;
import br.ufpr.tads.msbantadsauth.inbound.UpdateUser;
import br.ufpr.tads.msbantadsauth.outbound.LoginResponse;
import br.ufpr.tads.msbantadsauth.outbound.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordManager passwordManager;
    private final JwtService service;

    public LoginResponse login(@NonNull @Valid LoginRequest request) {
        log.debug("[retrieving] login '{}'", request.email());

        User user = this.repository
                .findUserByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user does not exists"));

        if (passwordManager.matches(request.password(), user.getPassword())) {
            String jwt = this.service.generateJwt(user);
            return new LoginResponse(jwt);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public List<UserResponse> findUsersById(List<Long> ids) {
        return this.repository.findAllById(ids).stream().map(UserResponse::ofEntity).toList();
    }

    @Transactional
    public UserResponse create(@NonNull CreateUser dto) {
        log.debug("[creating] {}: '{}'", dto.profileRole(), dto.email());
        Optional<User> optional = this.repository.findUserByEmail(dto.email());
        if (optional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exists");
        }

        CreateUser createUser = new CreateUser(dto.email(), dto.profileRole(), passwordManager.generateRandomPassword());
        var entity = new User(createUser);

        log.info("[creating] saving {} to database: '{}'", dto.profileRole(), dto.email());
        var saved = this.repository.save(entity);

        return UserResponse.ofEntity(saved);
    }

    @Transactional
    public void update(@Valid UpdateUser request) {
        log.debug("[updating] '{}'", request.userId());

        User user = this.repository
                .findById(request.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "user not found"));

        if (Objects.nonNull(request.email())) {
            log.debug("[updating] '{}': email", request.userId());
            user.setEmail(request.email().toLowerCase());
        }

        if (Objects.nonNull(request.password())) {
            log.debug("[updating] '{}': password", request.userId());
            user.setPassword(passwordManager.encode(request.password()));
        }

        log.info("[updating] saving '{}' to database", user.getId());
        this.repository.save(user);
    }
}
