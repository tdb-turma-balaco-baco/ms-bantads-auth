package br.ufpr.tads.msbantadsauth.user;

import br.ufpr.tads.msbantadsauth.inbound.LoginRequest;
import br.ufpr.tads.msbantadsauth.outbound.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public LoginResponse login(@NonNull @Valid LoginRequest request) {
        User user = this.repository
                .findUserByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user does not exists"));

        if (user.getPassword().equals(request.password())) {
            return new LoginResponse(user.getId(), user.getEmail(), user.getProfileRole());
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @Transactional
    public User create(@NonNull User entity) {
        Optional<User> optional = this.repository.findUserByEmail(entity.getEmail());
        if (optional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exists");
        }

        log.info("Creating {}: {}", entity.getProfileRole(), entity.getEmail());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return this.repository.save(entity);
    }
}
