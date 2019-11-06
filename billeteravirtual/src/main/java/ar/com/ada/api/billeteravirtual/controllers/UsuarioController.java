package ar.com.ada.api.billeteravirtual.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.excepciones.UsuarioNoAutorizadoException;
import ar.com.ada.api.billeteravirtual.services.PersonaService;
import ar.com.ada.api.billeteravirtual.services.UsuarioService;

/**
 * UsuarioController
 */
@RestController
public class UsuarioController {

    @Autowired
    UsuarioService us;

    @Autowired
    PersonaService ps;

    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios(Principal principal) throws UsuarioNoAutorizadoException {
        Usuario u = us.buscarPorUserName(principal.getName());

        if (u.getTipoUsuario().equals("Admin")) {
            List<Usuario> lu = us.getUsuarios();

            return lu;
        }
        throw new UsuarioNoAutorizadoException("El usuario no posee autorización para realizar esta acción.");
    }

    @GetMapping("/usuarios/{id}")
    public Usuario getUsuarioById(@PathVariable int id, Principal principal) throws UsuarioNoAutorizadoException {
        Usuario u = us.buscarPorUserName(principal.getName());

        if (u.getTipoUsuario().equals("Admin")) {
            Usuario usu = us.buscarPorId(id);

            return usu;
        }
        throw new UsuarioNoAutorizadoException("El usuario no posee autorización para realizar esta acción.");
    }
}