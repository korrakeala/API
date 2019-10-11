package ar.com.ada.api.billeteravirtual.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.services.UsuarioService;

/**
 * UsuarioController
 */
@RestController
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios()
    {
        List<Usuario> lu = usuarioService.getUsuarios();
        
        return lu;
    }

    @GetMapping("/usuarios/{id}")
    public Usuario getUsuarioById(@PathVariable int id)
    {
        Usuario u = usuarioService.buscarPorId(id);
        
        return u;
    }
}