package br.ufpr.tads.msbantadsauth.outbound;

import br.ufpr.tads.msbantadsauth.user.UserRoles;

public record LoginResponse(Long userId, String email, UserRoles role) {
}
