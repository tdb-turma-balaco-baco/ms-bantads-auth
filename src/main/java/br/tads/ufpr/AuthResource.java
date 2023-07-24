package br.tads.ufpr;

import br.tads.ufpr.dto.UserLoginRequest;
import br.tads.ufpr.dto.UserLoginResponse;
import br.tads.ufpr.model.User;
import br.tads.ufpr.model.UserType;
import br.tads.ufpr.services.AuthenticationService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Inject
    AuthenticationService authenticationService;

    @POST
    @Path("/login")
    public Response login(UserLoginRequest userLoginRequest) {
        try {
            if (userLoginRequest == null) {
                throw new IllegalArgumentException("Precisa ter os campos {email, password}");
            }

            UserLoginResponse response = authenticationService.login(
                    userLoginRequest.email(),
                    userLoginRequest.password());

            return Response.ok(response).build();
        } catch (NoSuchElementException | IllegalArgumentException e) {
            Log.error("Credenciais inválidas");
            throw new BadRequestException("Credenciais inválidas");
        } catch (Exception e) {
            Log.fatal("Ocorreu um erro inesperado:", e);
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/demo")
    public Response cadastrar(UserLoginRequest userLoginRequest) {
        User user = new User();

        user.setEmail(userLoginRequest.email());
        user.password = BcryptUtil.bcryptHash(userLoginRequest.password());
        Log.info("Senha encriptada: " + user.password);

        user.createdOn = LocalDateTime.now();
        user.userType = UserType.CLIENT;

        user.persist();

        return Response.created(URI.create("/auth/demo/" + user.id)).build();
    }
}
