package ar.com.ada.api.billeteravirtual.controllers;

import java.security.Principal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.billeteravirtual.entities.Persona;
import ar.com.ada.api.billeteravirtual.entities.Usuario;
import ar.com.ada.api.billeteravirtual.excepciones.UsuarioNoAutorizadoException;
import ar.com.ada.api.billeteravirtual.services.PersonaService;
import ar.com.ada.api.billeteravirtual.services.UsuarioService;

/**
 * PersonaController
 */
@RestController
public class PersonaController {

    @Autowired
    PersonaService ps;

    @Autowired
    UsuarioService us;

    /**
     * Este metodo tiene un queryString que viene por url y es opcional, este campo
     * servira para buscar las personas con un nombre especifico. ejemplo: /personas
     * -> devuelve la lista de todas las personas. En este caso la lista de personas
     * ordenadas usando el service+repo creado ejemplo2: /personas?nombre=Analia ->
     * trae todas las personas que tengan el nombre analia. la busqueda la hace a
     * traves del service+repo(query creado en repo)
     * 
     * @param nombre
     * @return
     * @throws UsuarioNoAutorizadoException
     */
    @GetMapping("/personas")
    public List<Persona> getPersonas(Principal principal,
            @RequestParam(value = "dni", required = false) String descripcion,
            @RequestParam(value = "nombre", required = false) String nombre) throws UsuarioNoAutorizadoException {
        Usuario u = us.buscarPorUserName(principal.getName());

        if (u.getTipoUsuario().equals("Admin")) {
            List<Persona> lp;
            if (nombre != null) {
                lp = ps.buscarTodosPorNombre(nombre);
                return lp;
            }
            if (descripcion != null) {
                lp = ps.buscarTodasPorDniUltimosDig(descripcion); //por qué no funciona éste método?
                return lp;
            }
            if (nombre == null && descripcion == null) { //por qué no funciona si uso equals?? me da NullPointerException
                lp = ps.buscarPersonasOrdenadoPorNombre();
                return lp;
            }
            /*if (nombre == null && descripcion == null) { //por qué no funciona si uso equals??
                lp = ps.buscarPersonasOrdenadoPorNombre();
                return lp;
            } else {
                if (nombre != null) {
                    lp = ps.buscarTodosPorNombre(nombre);
                    return lp;
                } else {
                    if (descripcion != null) {
                        lp = ps.buscarTodasPorDniUltimosDig(descripcion); //por qué no funciona éste método?
                        return lp;
                    }
                }
            }*/
        }
        throw new UsuarioNoAutorizadoException("El usuario no posee autorización para realizar esta acción.");
    }

    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable int id, Principal principal)
            throws UsuarioNoAutorizadoException {
        Usuario u = us.buscarPorUserName(principal.getName());

        if (u.getTipoUsuario().equals("Admin")) {
            Persona p = ps.buscarPorId(id);
            if (p == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(p);
        }
        throw new UsuarioNoAutorizadoException("El usuario no posee autorización para realizar esta acción.");
    }

}