package br.ufpr.tads.msbantadsauth.inbound;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record UpdateUser(@NotEmpty @Positive Long userId,
                         @Email String email,
                         @Length(min = 12) String password) {
}