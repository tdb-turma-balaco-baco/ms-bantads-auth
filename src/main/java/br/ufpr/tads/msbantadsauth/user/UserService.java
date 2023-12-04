package br.ufpr.tads.msbantadsauth.user;

import br.ufpr.tads.msbantadsauth.inbound.LoginRequest;
import br.ufpr.tads.msbantadsauth.outbound.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public LoginResponse login(@Valid LoginRequest request) {
        this.repository.findUserByEmail(request.email());
        return new LoginResponse(1L, "mock@email.com", UserRoles.CUSTOMER);
    }

    @Transactional
    public User create(final User entity) {
        log.info("Creating {}: {}", entity.getProfileRole(), entity.getEmail());
        return this.repository.save(entity);
    }
}
