package br.ufpr.tads.msbantadsauth.auth;

import br.ufpr.tads.msbantadsauth.inbound.CreateUser;
import br.ufpr.tads.msbantadsauth.inbound.LoginRequest;
import br.ufpr.tads.msbantadsauth.inbound.UpdateUser;
import br.ufpr.tads.msbantadsauth.user.ProfileRole;
import br.ufpr.tads.msbantadsauth.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {
    private final String URL = AuthController.ROUTE;

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private AuthService authService;
    @Autowired private UserRepository repository;

    @BeforeEach
    void setUp() {
        this.repository.deleteAll();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("should return 400 without email")
    void return400_withoutEmail(String email) throws Exception {
        var request = new LoginRequest(email, "password");
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(post(this.URL)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("should return 400 without password")
    void return400_withoutPassword(String password) throws Exception {
        var request = new LoginRequest("email@email.com", password);
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(post(this.URL)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"email", "email@123", "email@.com", ".com.@.com"})
    @DisplayName("should return 400 with invalid email")
    void return400_withInvalidEmail(String email) throws Exception {
        var request = new LoginRequest(email, "password");
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(post(this.URL)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 200 with valid login")
    void return200_validLogin() throws Exception {
        var response = this.authService.create(new CreateUser("email@email.com", ProfileRole.CUSTOMER, null));
        this.authService.update(new UpdateUser(response.userId(), null, "passwordLong"));

        var request = new LoginRequest("email@email.com", "passwordLong");
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(post(this.URL)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return 401 with invalid login")
    void return401_validLogin() throws Exception {
        var response = this.authService.create(new CreateUser("email@email.com", ProfileRole.CUSTOMER, null));
        this.authService.update(new UpdateUser(response.userId(), null, "passwordLong"));

        var request = new LoginRequest("email@email.com", "differentPassword");
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(post(this.URL)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("create")
    void create() {
    }

    @Test
    @DisplayName("update")
    void update() {
    }
}