package br.tads.ufpr.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@MongoEntity(collection = "User")
public class User extends PanacheMongoEntity {
    public ObjectId id;
    public String email;
    public String password;
    public boolean isBlocked;
    public UserType userType;
    public LocalDateTime createdOn;
    public LocalDateTime blockedOn;

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }
}
