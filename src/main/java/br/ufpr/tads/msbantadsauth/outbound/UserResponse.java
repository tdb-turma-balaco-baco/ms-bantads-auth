package br.ufpr.tads.msbantadsauth.outbound;

import br.ufpr.tads.msbantadsauth.user.ProfileRole;
import br.ufpr.tads.msbantadsauth.user.User;
import jakarta.validation.constraints.NotNull;

public record UserResponse(Long userId, String email, ProfileRole role) {
    public static UserResponse of(@NotNull User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getProfileRole());
    }
}
