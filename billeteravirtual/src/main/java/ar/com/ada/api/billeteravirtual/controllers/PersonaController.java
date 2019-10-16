package ar.com.ada.api.billeteravirtual.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

    @GetMapping("/personas")
    public List<Persona> getPersonas(@RequestParam(value = "nombre", required = false) String nombre)
    {
        List<Persona> lp;
        if (nombre == null) {
            lp = personaService.buscarPersonasOrdenadoPorNombre(); //findAllOrderByNombre namedQuery en Persona
        } else {
            lp = personaService.buscarTodosPorNombre(nombre); //findAllByNombreContiene namedQuery en Persona
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