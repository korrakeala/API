package ar.com.ada.api.climate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.climate.entities.Pais;
import ar.com.ada.api.climate.entities.Temperatura;
import ar.com.ada.api.climate.exceptions.TemperaturaException;
import ar.com.ada.api.climate.repos.PaisRepository;

/**
 * PaisService
 */
@Service
public class PaisService {

    @Autowired
    PaisRepository repo;

    public int altaPais(int codigo, String nombre) {
        Pais p = new Pais();
        p.setCodigoPais(codigo);
        p.setNombre(nombre);
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
        repo.save(p);
        return p;
    }

    public List<Temperatura> getTemperaturasPorPais(int codigoPais){
        for (Pais p : repo.findAll()) {
            if (p.getCodigoPais() == codigoPais) {
                Pais pa = buscarPorId(codigoPais);
                return pa.getTemperaturas();
            }
        }
        return null;
    }
}