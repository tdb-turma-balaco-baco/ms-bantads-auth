package br.ufpr.tads.msbantadsauth.user;

import br.ufpr.tads.msbantadsauth.inbound.CreateUser;
import br.ufpr.tads.msbantadsauth.inbound.UpdateUser;
import br.ufpr.tads.msbantadsauth.outbound.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public User findUserByEmail(@Valid @NonNull @Email String email) {
        log.debug("[retrieving] findUserByEmail '{}'", email);

        return this.repository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public List<UserResponse> findUsersById(@NonNull List<Long> ids) {
        return this.repository.findAllById(ids).stream().map(UserResponse::ofEntity).toList();
    }

    @Transactional
    public UserResponse create(@Valid @NonNull CreateUser dto) {
        log.debug("[creating] {}: '{}'", dto.profileRole(), dto.email());
        Optional<User> optional = this.repository.findUserByEmail(dto.email());

        if (optional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exists");
        }

        var entity = new User(dto);

        log.info("[creating] saving {} to database: '{}'", dto.profileRole(), dto.email());
        var saved = this.repository.save(entity);

        return UserResponse.ofEntity(saved);
    }

    @Transactional
    public UserResponse update(@Valid @NonNull UpdateUser updateUser) {
        log.debug("[updating] '{}'", updateUser.userId());

        User user = this.repository
                .findById(updateUser.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "user not found"));

        if (Objects.nonNull(updateUser.email())) {
            log.debug("[updating] '{}': email", updateUser.userId());
            user.setEmail(updateUser.email().toLowerCase());
        }

        if (Objects.nonNull(updateUser.password())) {
            log.debug("[updating] '{}': password", updateUser.userId());
            user.setPassword(updateUser.password());
        }

        log.info("[updating] saving '{}' to database", user.getId());
        return UserResponse.ofEntity(this.repository.save(user));
    }
}
