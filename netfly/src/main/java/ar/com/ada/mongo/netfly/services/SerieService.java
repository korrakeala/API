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

    public enum SerieValidationType {
        
        SERIE_OK,
        TEMPORADAS_NULA, 
        TEMPORADAS_VACIA, 
        TEMPORADA_DUPLICADA, 
        TEMPORADA_INVALIDA,

        SERIE_DATOS_INVALIDOS 

    }

    public SerieValidationType verificarSerie(Serie serie) {

        if (serie.getNombre() == null)
            return SerieValidationType.SERIE_DATOS_INVALIDOS;

        if (serie.getAnio() <= 0)
            return SerieValidationType.SERIE_DATOS_INVALIDOS;

        if (serie.getTemporadas() == null)
            return SerieValidationType.TEMPORADAS_NULA;

        if (serie.getTemporadas().size() == 0)
            return SerieValidationType.TEMPORADAS_VACIA;

        //Armo un hashmap para ver si la temporada esta duplicada
        HashMap<Integer, Temporada> unicasTemps = new HashMap<>();

        for (Temporada t : serie.getTemporadas()) {
            if (unicasTemps.containsKey(new Integer(t.getNroTemporada())))
                return SerieValidationType.TEMPORADA_DUPLICADA;
                
            if (t.getEpisodios().size() == 0)
                return SerieValidationType.TEMPORADA_INVALIDA;
        
        unicasTemps.put(new Integer(t.getNroTemporada()), t);
        
            }

        return SerieValidationType.SERIE_OK;
    }


}