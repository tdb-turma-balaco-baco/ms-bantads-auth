package br.tads.ufpr.model;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<User> {
    public Optional<User> findByEmail(String email) {
        return find("email", email)
                .stream()
                .findFirst();
    }
}
