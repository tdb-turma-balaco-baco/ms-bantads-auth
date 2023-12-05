package br.ufpr.tads.msbantadsauth.inbound;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record UpdateUser(@NotNull @Positive Long userId,
                         @Email @Length(min = 6) String email,
                         @Length(min = 12) String password) {
}