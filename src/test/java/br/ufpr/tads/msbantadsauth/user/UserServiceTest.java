package br.ufpr.tads.msbantadsauth.user;

import br.ufpr.tads.msbantadsauth.auth.PasswordManager;
import br.ufpr.tads.msbantadsauth.inbound.CreateUser;
import br.ufpr.tads.msbantadsauth.inbound.UpdateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    private static final CreateUser CREATE_USER = new CreateUser("customer@email.com", ProfileRoles.CUSTOMER, "");

    @Autowired private UserService service;
    @Autowired private UserRepository repository;
    @Autowired private PasswordManager passwordManager;

    @BeforeEach
    void setUp() {
        this.repository.deleteAll();
    }

    @Test
    @DisplayName("should successfully create a user")
    void create() {
        var response = this.service.create(CREATE_USER);

        assertEquals(1, this.repository.count());
        assertNotNull(response.userId());
        assertEquals(CREATE_USER.email(), response.email());
        assertEquals(CREATE_USER.profileRole(), response.role());
    }

    @Test
    @DisplayName("should throw conflict when creating with same email")
    void create_withExistingUser() {
        this.repository.save(new User(CREATE_USER));

        assertThrows(ResponseStatusException.class, () -> this.service.create(CREATE_USER));
        assertEquals(1, this.repository.count());
    }

    @Test
    @DisplayName("should update all user values successfully")
    void update() {
        var created = this.repository.save(new User(CREATE_USER));
        UpdateUser dto = new UpdateUser(created.getId(), "newEmail@email.com", "newPassword");

        this.service.update(dto);
        User updated = this.repository.findById(created.getId()).orElseThrow(AssertionError::new);

        assertEquals(dto.email().toLowerCase(), updated.getEmail());
        assertTrue(passwordManager.matches(dto.password(), updated.getPassword()));
    }

    @Test
    @DisplayName("should update some of the user values")
    void updatePartial() {
        var created = this.repository.save(new User(CREATE_USER));
        List<Long> ids = this.repository.findAll().stream().map(User::getId).toList();

        UpdateUser request = new UpdateUser(ids.get(0), "newEmail@email.com", null);
        this.service.update(request);

        User entity = this.repository.findById(ids.get(0)).orElseThrow(AssertionError::new);
        assertEquals("newemail@email.com", entity.getEmail());
        assertEquals(created.getPassword(), entity.getPassword());
    }
}