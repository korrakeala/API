package ar.com.ada.api.climate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.climate.entities.Pais;
import ar.com.ada.api.climate.entities.Temperatura;
import ar.com.ada.api.climate.exceptions.CodigoPaisException;
import ar.com.ada.api.climate.repos.PaisRepository;

/**
 * PaisService
 */
@Service
public class PaisService {

    @Autowired
    PaisRepository repo;

    public int altaPais(int codigo, String nombre) throws CodigoPaisException {
        Pais p = new Pais(codigo, nombre);
        for (Pais pa : repo.findAll()) {
            if (pa.getCodigoPais() == codigo) {
                throw new CodigoPaisException("El código "+ codigo +" ya está asignado al país "+ pa.getNombre());
            }
        }
        repo.save(p);
        return p.getCodigoPais();
    }

    public List<Pais> listarPaises() {
        return repo.findAll();
    }

    public Pais buscarPorId(int id) {

        Optional<Pais> p = repo.findById(id);

        if (p.isPresent()) {
            return p.get();
        }
        return null;
    }

    public Pais updatePais(int id, String nombre) {
        Pais p = new Pais();
        p.setNombre(nombre);
        p.setCodigoPais(id);
        repo.save(p);
        return p;
    }

    public List<Temperatura> getTemperaturasPorPais(int codigoPais){
        Pais p = this.buscarPorId(codigoPais);
        return p.getTemperaturas();
    }
}