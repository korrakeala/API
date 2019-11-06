package ar.com.ada.mongo.netfly.services;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.mongo.netfly.entities.*;
import ar.com.ada.mongo.netfly.exceptions.ContenidoInexistenteException;
import ar.com.ada.mongo.netfly.repo.SerieRepository;

/**
 * SerieService
 */
@Service
public class SerieService {

    @Autowired
    SerieRepository repo;

    public List<Serie> getSeries() {

        return repo.findAll();
    }

    public Serie buscarPorId(String id) throws ContenidoInexistenteException {

        Optional<Serie> u = repo.findById(new ObjectId(id));

        if (u.isPresent())
            return u.get();
        throw new ContenidoInexistenteException("Serie inexistente.");
    }

    public void grabar(Serie s) {
        repo.save(s);
    }

    public Serie crearSerie(String nombre, String genero, Integer anio, Temporada t, Episodio e) {
        Serie s = new Serie();
        s.setNombre(nombre);
        s.setGenero(genero);
        s.setAnio(anio);

        t.getEpisodios().add(e);
        s.getTemporadas().add(t);

        repo.save(s);
        return s;
    }

    /*public Temporada crearTemporada(int nroTemporada) {
        Temporada t = new Temporada();
        t.setNroTemporada(nroTemporada);
        return t;
    }

    public Episodio crearEpisodio(int nroEpisodio, String titulo, int duracionMin) {
        Episodio e = new Episodio();
        e.setNroEpisodio(nroEpisodio);
        e.setTitulo(titulo);
        e.setDuracionMin(duracionMin);
        return e;
    }*/

    public Temporada agregarTemporada(String nombreSerie, Temporada t) {
        Serie s = repo.findByNombre(nombreSerie);
        s.getTemporadas().add(t);
        repo.save(s);

        return t;
    }

    public Episodio agregarEpisodio(String nombreSerie, Integer nroTemp, Episodio e) throws ContenidoInexistenteException {
        Serie s = repo.findByNombre(nombreSerie);
        Temporada t = s.getTemporada(nroTemp);
        t.getEpisodios().add(e);

        repo.save(s);

        return e;
    }

    public Episodio modificarEpisodio(String nombreSerie, Integer nroTemp, Integer nroEpisodio, Episodio e) throws ContenidoInexistenteException {
        Serie s = repo.findByNombre(nombreSerie);
        Temporada t = s.getTemporada(nroTemp);
        t.getEpisodios().remove(t.getEpisodioNro(nroEpisodio));
        t.getEpisodios().add(e);
        repo.save(s);

        return e;
    }

}