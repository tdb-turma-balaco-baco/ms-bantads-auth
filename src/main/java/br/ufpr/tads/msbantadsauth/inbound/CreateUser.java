package br.ufpr.tads.msbantadsauth.inbound;

import br.ufpr.tads.msbantadsauth.user.ProfileRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateUser(@NotEmpty @Email String email,
                         @NotNull ProfileRole profileRole,
                         String password) {
}
