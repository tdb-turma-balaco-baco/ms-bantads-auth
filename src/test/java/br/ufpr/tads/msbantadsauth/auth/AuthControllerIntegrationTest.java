package br.ufpr.tads.msbantadsauth.auth;

import br.ufpr.tads.msbantadsauth.inbound.CreateUser;
import br.ufpr.tads.msbantadsauth.inbound.LoginRequest;
import br.ufpr.tads.msbantadsauth.inbound.UpdateUser;
import br.ufpr.tads.msbantadsauth.user.ProfileRole;
import br.ufpr.tads.msbantadsauth.user.User;
import br.ufpr.tads.msbantadsauth.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {
    private static final String URL = AuthController.ROUTE;
    private static final String VALID_PASSWORD = "passwordLong";
    private static final String EMAIL = "email@email.com";
    private static final User CREATE_USER = User.create(new CreateUser(EMAIL, ProfileRole.CUSTOMER, "updateMe"));

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
    @ValueSource(strings = {"email", "email@.com", ".com.@.com"})
    @DisplayName("should return 400 logging in with invalid email")
    void return400_withoutEmail(String invalidEmail) throws Exception {
        var request = new LoginRequest(invalidEmail, VALID_PASSWORD);
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(post(URL)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("should return 400 logging in without password")
    void return400_withoutPassword(String password) throws Exception {
        var request = new LoginRequest(EMAIL, password);
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(post(URL)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 401 logging in with incorrect password")
    void return401_validLogin() throws Exception {
        var response = this.authService.create(new CreateUser(EMAIL, ProfileRole.CUSTOMER, null));
        this.authService.update(new UpdateUser(response.userId(), null, VALID_PASSWORD));

        var request = new LoginRequest(EMAIL, "differentPassword");
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(post(URL)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("should return 200 logging in with valid request")
    void return200_validLogin() throws Exception {
        var response = this.authService.create(new CreateUser(EMAIL, ProfileRole.CUSTOMER, null));
        this.authService.update(new UpdateUser(response.userId(), null, VALID_PASSWORD));

        var request = new LoginRequest(EMAIL, VALID_PASSWORD);
        var json = objectMapper.writeValueAsString(request);

        var result = this.mockMvc
                .perform(post(URL)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().startsWith("{\"token\":\""));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"email", "email@.com", ".com.@.com"})
    @DisplayName("should return 400 when creating a user with invalid email")
    void return400_createWithInvalidEmail(String invalidEmail) throws Exception {
        var request = new CreateUser(invalidEmail, ProfileRole.CUSTOMER, null);
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(post(URL + "/user")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        assertEquals(0, this.repository.count());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("should return 400 when creating a user with invalid email")
    void return400_createWithInvalidProfileRole(ProfileRole emptyRole) throws Exception {
        var request = new CreateUser(EMAIL, emptyRole, null);
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(post(URL + "/user")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        assertEquals(0, this.repository.count());
    }

    @ParameterizedTest
    @EnumSource(ProfileRole.class)
    @DisplayName("should create a new user successfully")
    void return201_create(ProfileRole profileRole) throws Exception {
        var request = new CreateUser(EMAIL, profileRole, null);
        var json = objectMapper.writeValueAsString(request);

        var result = this.mockMvc
                .perform(post(URL + "/user")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(1, this.repository.count());

        var saved = this.repository.findUserByEmail(EMAIL).orElseThrow(AssertionError::new);
        assertEquals(result.getResponse().getHeader("Location"), this.URL + "/user/" + saved.getId());
        assertNotNull(saved.getPassword());
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"email", "email@.com", ".com.@.com"})
    @DisplayName("should return 400 when update email is invalid")
    void return400_invalidEmailRequest(String invalidEmail) throws Exception {
        var userId = this.repository.save(CREATE_USER).getId();

        var request = new UpdateUser(userId, invalidEmail, null);
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(put(URL + "/user/" + userId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        User notUpdated = this.repository.findById(userId).orElseThrow(AssertionError::new);
        assertEquals(CREATE_USER.getEmail(), notUpdated.getEmail());
        assertEquals(CREATE_USER.getPassword(), notUpdated.getPassword());
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"shortString", "shorter"})
    @DisplayName("should return 400 when update password is invalid")
    void return400_invalidPasswordRequest(String invalidPassword) throws Exception {
        var userId = this.repository.save(CREATE_USER).getId();

        var request = new UpdateUser(userId, null, invalidPassword);
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(put(URL + "/user/" + userId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        User notUpdated = this.repository.findById(userId).orElseThrow(AssertionError::new);
        assertEquals(CREATE_USER.getEmail(), notUpdated.getEmail());
        assertEquals(CREATE_USER.getPassword(), notUpdated.getPassword());
    }

    @Test
    @DisplayName("should return 422 when update request updates no value")
    void return422_updateNoValue() throws Exception {
        var userId = this.repository.save(CREATE_USER).getId();

        var request = new UpdateUser(userId, null, null);
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(put(URL + "/user/" + userId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));

        User notUpdated = this.repository.findById(userId).orElseThrow(AssertionError::new);
        assertEquals(CREATE_USER.getEmail(), notUpdated.getEmail());
        assertEquals(CREATE_USER.getPassword(), notUpdated.getPassword());
    }

    @Test
    @DisplayName("should return 400 when user try to update userId")
    void return400_updateUserId() throws Exception {
        var userId = this.repository.save(CREATE_USER).getId();

        var request = new UpdateUser(userId, null, null);
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(put(URL + "/user/" + (userId + 1L))
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        User notUpdated = this.repository.findById(userId).orElseThrow(AssertionError::new);
        assertEquals(CREATE_USER.getEmail(), notUpdated.getEmail());
        assertEquals(CREATE_USER.getPassword(), notUpdated.getPassword());
    }

    @Test
    @DisplayName("should return 200 when user updated sucessfully the email")
    void return200_updateEmail() throws Exception {
        var userId = this.repository.save(CREATE_USER).getId();

        var request = new UpdateUser(userId, "newemail@email.com", null);
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(put(URL + "/user/" + userId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        User updated = this.repository.findById(userId).orElseThrow(AssertionError::new);
        assertEquals(request.email(), updated.getEmail());
        assertEquals(CREATE_USER.getPassword(), updated.getPassword());
    }

    @Test
    @DisplayName("should return 200 when user updated sucessfully the password")
    void return200_updatePassword() throws Exception {
        var userId = this.repository.save(CREATE_USER).getId();

        var request = new UpdateUser(userId, null, VALID_PASSWORD);
        var json = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(put(URL + "/user/" + userId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        User updated = this.repository.findById(userId).orElseThrow(AssertionError::new);
        assertEquals(CREATE_USER.getEmail(), updated.getEmail());
        assertNotEquals(CREATE_USER.getPassword(), updated.getPassword());
    }
}