package ar.com.ada.api.billeteravirtual.controllers;

import java.security.Principal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.billeteravirtual.entities.Persona;
import ar.com.ada.api.billeteravirtual.services.PersonaService;

/**
 * PersonaController
 */
@RestController
public class PersonaController {
    
    @Autowired
    PersonaService personaService;

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
     */
    @GetMapping("/personas")
    public List<Persona> getPersonas(Authentication authentication, Principal principal, @RequestParam(value = "nombre", required = false) String nombre) {
        List<Persona> lp;
        
        if (nombre == null) {
            lp = personaService.buscarPersonasOrdenadoPorNombre();
        } else {
            lp = personaService.buscarTodosPorNombre(nombre);
        }

        return lp;
    }

    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable int id) {
        Persona p = personaService.buscarPorId(id);

        if (p == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(p);
    }

    //@GetMapping("/personas")
    public List<Persona> getPersonasByDNIUltimosDig(@RequestParam(value = "dni", required = false) String descripcion){
        List<Persona> lp = personaService.buscarTodasPorDniUltimosDig(descripcion);
        return lp;
    }

    
}