package br.ufpr.tads.msbantadsauth.inbound;

import br.ufpr.tads.msbantadsauth.user.ProfileRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CreateUser(@NotEmpty @Email String email,
                         @NotEmpty ProfileRole profileRole,
                         String password) {
}
