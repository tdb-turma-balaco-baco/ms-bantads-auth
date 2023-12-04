package br.ufpr.tads.msbantadsauth.outbound;

import br.ufpr.tads.msbantadsauth.user.ProfileRoles;
import br.ufpr.tads.msbantadsauth.user.User;

public record UserResponse(Long userId, String email, ProfileRoles role) {
    public static UserResponse ofEntity(User entity) {
        return new UserResponse(entity.getId(), entity.getEmail(), entity.getProfileRole());
    }
}
