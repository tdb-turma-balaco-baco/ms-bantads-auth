package br.tads.ufpr.dto;

import br.tads.ufpr.model.UserType;
import org.bson.types.ObjectId;

public record UserLoginResponse(ObjectId id, String email, UserType userType) {
}
