package ar.com.ada.spookify.controllers;

import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.spookify.entities.Cancion;

/**
 * CancionController
 */
@RestController
public class CancionController {

    @GetMapping(value = "/temas")
    public List<Cancion> getTodas(){

        List<Cancion> temas = new  ArrayList<Cancion>();
        Cancion c = new Cancion();

        c.setTitulo("Take on me");
        c.setDuracion(3.48);

        temas.add(c);

        c = new Cancion();
        c.setTitulo("Truth Hurts");
        c.setDuracion(3.01);
        
        temas.add(c);

        return temas;

        
    }
}