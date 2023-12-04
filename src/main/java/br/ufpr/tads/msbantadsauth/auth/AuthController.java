package br.ufpr.tads.msbantadsauth.auth;

import br.ufpr.tads.msbantadsauth.inbound.LoginRequest;
import br.ufpr.tads.msbantadsauth.outbound.LoginResponse;
import br.ufpr.tads.msbantadsauth.user.User;
import br.ufpr.tads.msbantadsauth.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping(value = AuthController.ROUTE)
@RequiredArgsConstructor
public class AuthController {
    public static final String ROUTE = "/api/auth";

    private final UserService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        User saved = this.service.create(user);
        return ResponseEntity.created(URI.create(ROUTE + "/" + saved.getId())).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = this.service.login(request);
        return ResponseEntity.ok(response);
    }
}
