package br.ufpr.tads.msbantadsauth.inbound;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record LoginRequest(@Email @NotEmpty String email, @NotEmpty @Length(min = 12) String password) {
}
