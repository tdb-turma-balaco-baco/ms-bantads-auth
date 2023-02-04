package br.ufpr.tads.msbantadsauth.Presentation.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.tads.msbantadsauth.Application.Services.Authentication.IUserAuthentication;
import br.ufpr.tads.msbantadsauth.Presentation.Contracts.UserLoginRequest;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    IUserAuthentication _userAuthentication;

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UserLoginRequest user) {
        try {
            boolean loginSuccess = _userAuthentication.login(user.getEmail(), user.getPassword());

            if (loginSuccess) 
                return ResponseEntity.ok(loginSuccess);
                
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginSuccess);
            
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
