package ar.com.ada.spookify.controllers;

import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.spookify.entities.Album;
import ar.com.ada.spookify.entities.Cancion;

/**
 * AlbumController
 */
@RestController
public class AlbumController {

    @GetMapping(value = "/albumes")
    public List<Album> getAlbum(){

        List<Album> albumes = new ArrayList<Album>();
        List<Cancion> canciones = new ArrayList<Cancion>();
        Cancion c = new Cancion();
        
        Album a = new Album();
        a.setArtista("Gorillaz");
        a.setTituloAlbum("The now now");
        c.setTitulo("Humility");
        c.setDuracion(3.17);
        canciones.add(c);

        c = new Cancion();
        c.setTitulo("Tranz");
        c.setDuracion(2.42);
        canciones.add(c);

        a.setCanciones(canciones);
        
        albumes.add(a);

        canciones = new ArrayList<Cancion>(); 
        
        a = new Album();
        a.setArtista("Stromae");
        a.setTituloAlbum("racine carrée");

        c = new Cancion();
        c.setTitulo("Formidable");
        c.setDuracion(3.33);
        canciones.add(c);

        c = new Cancion();
        c.setTitulo("carmen");
        c.setDuracion(3.09);
        canciones.add(c);
        a.setCanciones(canciones);

        albumes.add(a);

        return albumes;

    }
    
}