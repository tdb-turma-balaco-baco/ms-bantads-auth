package br.ufpr.tads.msbantadsauth.rest;

import br.ufpr.tads.msbantadsauth.model.Usuario;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@CrossOrigin
@RestController
public class UsuarioREST {
    public static List<Usuario> usuarios = new ArrayList<>();

    @GetMapping("/usuarios")
    public List<Usuario> obterTodosUsuarios() {
        return usuarios;
    }

    @GetMapping("/usuarios/{id}")
    public Usuario obterUsuarioPorId(@PathVariable("id") int id) {
        Usuario usuario = usuarios.stream().filter(usu -> usu.getId() == id).findFirst().orElse(null);
        return usuario;
    }

    @PostMapping("/usuarios")
    public Usuario inserirUsuario(@RequestBody Usuario novoUsuario) {
        Usuario u = usuarios.stream().max(Comparator.comparing(Usuario::getId)).orElse(null);

        if (u == null) {
            novoUsuario.setId(1);
        } else {
            novoUsuario.setId(u.getId() + 1);
        }

        usuarios.add(novoUsuario);
        return novoUsuario;
    }

    @PutMapping("/usuarios/{id}")
    public Usuario atualizarUsuario(@PathVariable("id") int id, @RequestBody Usuario usuario) {
        Usuario usu = usuarios.stream().filter(u -> u.getId() == id).findFirst().orElse(null);

        if (usu != null) {
            usu.setNome(usuario.getNome());
            usu.setLogin(usuario.getLogin());
            usu.setSenha(usuario.getSenha());
            usu.setPerfil(usuario.getPerfil());
        }

        return usu;
    }

    @DeleteMapping("/usuarios/{id}")
    public Usuario removerUsuario(@PathVariable("id") int id) {
        Usuario usuario = usuarios.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
        if (usuario != null) {
            usuarios.remove(usuario);
        }
        return usuario;
    }

    static {
        usuarios.add(new Usuario(1, "administrador", "admin", "admin", "ADMINISTRADOR"));
        usuarios.add(new Usuario(2, "gerente", "gerente", "gerente", "GERENTE"));
        usuarios.add(new Usuario(3, "funcionario", "func", "func", "FUNCIONARIO"));
    }
}
