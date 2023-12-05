package br.ufpr.tads.msbantadsauth;

import br.ufpr.tads.msbantadsauth.auth.AuthController;
import br.ufpr.tads.msbantadsauth.auth.AuthService;
import br.ufpr.tads.msbantadsauth.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MsBantadsAuthApplicationTests {
	@Autowired private UserService userService;
	@Autowired private AuthService authService;
	@Autowired private AuthController authController;

	@Test
	void contextLoads() {
		assertNotNull(userService);
		assertNotNull(authService);
		assertNotNull(authController);
	}

}
