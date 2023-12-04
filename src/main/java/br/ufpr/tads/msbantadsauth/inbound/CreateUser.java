package br.ufpr.tads.msbantadsauth.inbound;

import br.ufpr.tads.msbantadsauth.user.ProfileRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record CreateUser(@NotEmpty @Email String email,
                         @NotEmpty ProfileRoles profileRole,
                         String password) {
}
