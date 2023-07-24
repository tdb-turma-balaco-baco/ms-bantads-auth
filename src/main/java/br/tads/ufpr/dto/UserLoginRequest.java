package br.tads.ufpr.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
        @Email @NotBlank(message = "Email não pode ser vazio")
        String email,
        @NotBlank(message = "Senha não pode ser vazia")
        String password
) { }
