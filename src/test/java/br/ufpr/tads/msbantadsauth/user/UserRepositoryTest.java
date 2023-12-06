package br.ufpr.tads.msbantadsauth.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired private UserRepository repository;

    @BeforeEach
    void setUp() {
        this.repository.deleteAll();
    }

    @ParameterizedTest
    @EnumSource(ProfileRole.class)
    @DisplayName("should find registered user when searching by email")
    void findUserByEmail(ProfileRole profileRole) {
        User entity = new User();
        entity.setEmail("email@email.com");
        entity.setPassword("password");
        entity.setProfileRole(profileRole);

        User saved = this.repository.save(entity);
        assertNotNull(saved);
        assertEquals(saved.getEmail(), entity.getEmail());
        assertEquals(saved.getPassword(), entity.getPassword());
        assertEquals(saved.getProfileRole(), entity.getProfileRole());
        assertTrue(saved.isActive());
    }
}