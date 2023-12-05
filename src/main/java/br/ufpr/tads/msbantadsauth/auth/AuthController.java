package br.ufpr.tads.msbantadsauth.auth;

import br.ufpr.tads.msbantadsauth.inbound.CreateUser;
import br.ufpr.tads.msbantadsauth.inbound.LoginRequest;
import br.ufpr.tads.msbantadsauth.inbound.UpdateUser;
import br.ufpr.tads.msbantadsauth.outbound.LoginResponse;
import br.ufpr.tads.msbantadsauth.outbound.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(value = AuthController.ROUTE)
@RequiredArgsConstructor
public class AuthController {
    public static final String ROUTE = "/api/auth";

    private final AuthService service;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        log.debug("[request] login '{}'", request.email());
        LoginResponse response = this.service.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user")
    public ResponseEntity<?> create(@RequestBody @Valid CreateUser user) {
        log.debug("[request] create {}", user);
        var saved = this.service.create(user);
        return ResponseEntity.created(URI.create(ROUTE + "/user/" + saved.userId())).build();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable @Positive Long id,
                                               @RequestBody @Valid UpdateUser user) {
        log.debug("[request] update {}", user.userId());

        if (!Objects.equals(id, user.userId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId can't be updated");
        }

        if (Objects.isNull(user.email()) && Objects.isNull(user.password())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "no user value to update");
        }

        UserResponse updated = this.service.update(user);
        return ResponseEntity.ok(updated);
    }
}
