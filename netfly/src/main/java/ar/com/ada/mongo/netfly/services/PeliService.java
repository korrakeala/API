package ar.com.ada.mongo.netfly.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.mongo.netfly.entities.Peli;
import ar.com.ada.mongo.netfly.exceptions.ContenidoInexistenteException;
import ar.com.ada.mongo.netfly.repo.PeliRepository;

/**
 * PeliService
 */
@Service
public class PeliService {

    @Autowired
    PeliRepository repo;

    public List<Peli> getPelis() {

        return repo.findAll();
    }

    public Peli buscarPorId(String id) throws ContenidoInexistenteException {

        Optional<Peli> u = repo.findById(new ObjectId(id));

        if (u.isPresent())
            return u.get();
        throw new ContenidoInexistenteException("Película inexistente.");
    }

    public void grabar(Peli p) {
        repo.save(p);
    }

    /*public Peli crearPeli(String nombre, String genero, Integer anio, boolean ganoOscar) {
        Peli p = new Peli();
        p.setNombre(nombre);
        p.setGenero(genero);
        p.setAnio(anio);
        p.setGanoOscar(ganoOscar);
        repo.save(p);
        return p;
    }

    public Peli updatePeli(ObjectId peliId, String nombre, String genero, Integer anio, boolean ganoOscar) {
        Peli p = repo.findBy_id(peliId);
        p.setNombre(nombre);
        p.setGenero(genero);
        p.setAnio(anio);
        p.setGanoOscar(ganoOscar);
        repo.save(p);
        return p;
    }*/

    public Peli modificarPeli(String id, Peli p) throws ContenidoInexistenteException {
        repo.delete(this.buscarPorId(id));
        repo.save(p);

        return p;
    }
}