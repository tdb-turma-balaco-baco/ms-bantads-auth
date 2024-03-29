package br.ufpr.tads.msbantadsauth.user;

import br.ufpr.tads.msbantadsauth.inbound.CreateUser;
import br.ufpr.tads.msbantadsauth.inbound.UpdateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    private static final CreateUser CREATE_USER = new CreateUser("customer@email.com", ProfileRole.CUSTOMER, "");

    @Autowired private UserService service;
    @Autowired private UserRepository repository;

    @BeforeEach
    void setUp() {
        this.repository.deleteAll();
    }

    @ParameterizedTest
    @EnumSource(ProfileRole.class)
    @DisplayName("should successfully create a user for all roles")
    void create(ProfileRole role) {
        CreateUser createUser = new CreateUser("customer@email.com", role, "");
        var response = this.service.create(createUser);

        assertEquals(1, this.repository.count());
        assertNotNull(response.userId());
        assertEquals(createUser.email(), response.email());
        assertEquals(createUser.profileRole(), response.role());
    }

    @Test
    @DisplayName("should throw conflict when creating with same email")
    void create_withExistingUser() {
        this.repository.save(User.create(CREATE_USER));

        assertThrows(ResponseStatusException.class, () -> this.service.create(CREATE_USER));
        assertEquals(1, this.repository.count());
    }

    @Test
    @DisplayName("should update all user values successfully")
    void update() {
        var created = this.repository.save(User.create(CREATE_USER));
        UpdateUser dto = new UpdateUser(created.getId(), "newEmail@email.com", "newPassword");

        this.service.update(dto);
        User updated = this.repository.findById(created.getId()).orElseThrow(AssertionError::new);

        assertEquals(dto.email().toLowerCase(), updated.getEmail());
        assertNotEquals(created.getPassword(), updated.getPassword());
    }

    @Test
    @DisplayName("should update some of the user values")
    void updatePartial() {
        var created = this.repository.save(User.create((CREATE_USER)));
        List<Long> ids = this.repository.findAll().stream().map(User::getId).toList();

        UpdateUser request = new UpdateUser(ids.get(0), "newEmail@email.com", null);
        this.service.update(request);

        User entity = this.repository.findById(ids.get(0)).orElseThrow(AssertionError::new);
        assertEquals("newemail@email.com", entity.getEmail());
        assertEquals(created.getPassword(), entity.getPassword());
    }
}