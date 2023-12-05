package br.ufpr.tads.msbantadsauth.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Test
    @DisplayName("should find registered user when searching by email")
    void findUserByEmail() {
        User entity = new User();
        entity.setEmail("email@email.com");
        entity.setPassword("password");
        entity.setProfileRole(ProfileRole.CUSTOMER);

        User saved = this.repository.save(entity);
        assertNotNull(saved);
        assertEquals(saved.getEmail(), entity.getEmail());
        assertEquals(saved.getProfileRole(), entity.getProfileRole());
    }
}