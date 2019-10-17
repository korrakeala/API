package ar.com.ada.api.climate.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.climate.entities.Pais;
import ar.com.ada.api.climate.entities.Temperatura;
import ar.com.ada.api.climate.exceptions.TemperaturaExistenteException;
import ar.com.ada.api.climate.repos.TemperaturaRepository;

/**
 * TemperaturaService
 */
@Service
public class TemperaturaService {

    @Autowired
    TemperaturaRepository repo;

    @Autowired
    PaisService ps;

    public Temperatura altaTemperatura(int codigoPais, int anio, double grados) throws TemperaturaExistenteException {
        Temperatura t = new Temperatura();
        t.setPais(ps.buscarPorId(codigoPais));
        t.setAnio(anio);
        t.setGrados(grados);
        for (Temperatura temp : t.getPais().getTemperaturas()) {
            if (temp.getAnio() == t.getAnio()) {
                throw new TemperaturaExistenteException("Ya existe una temperatura para el año " + anio);
            }
        }
        repo.save(t);
        return t;
    }

    public Temperatura buscarPorId(int id) {

        Optional<Temperatura> t = repo.findById(id);

        if (t.isPresent()) {
            return t.get();
        }
        return null;
    }

    public Temperatura bajaTemperatura(int id) {
        Temperatura t = this.buscarPorId(id);
        t.setAnio(0);
        repo.save(t);

        return t;
    }

    public List<Temperatura> listarTemperaturasPorAnio(int anio) {
        List<Temperatura> lt = new ArrayList<Temperatura>();

        for (Temperatura t : repo.findAllByAnio(anio)) {
            if (t.getAnio() == anio) {
                lt.add(t);
            }
        }

        return lt;
    }
    /*
     * [{ “nombrePais”: “Argentina”, “grados”: 29 },
     * 
     * { “nombrePais”: “Venezuela”, “grados”: 45 }]
     * 
     */

    public Temperatura buscarTemperaturaMaximaPais(int codigoPais) {
        Pais p = ps.buscarPorId(codigoPais);
        double tmax;
        tmax = -100;
        Temperatura tempEncontrada = new Temperatura();
        for (Temperatura t : p.getTemperaturas()) {
            if (t.getGrados() >= tmax) {
                tmax = t.getGrados();
                tempEncontrada = t;
            } 
        }
        return tempEncontrada;
    }

    public List<Temperatura> buscarTodasPorAnio(int anio) {

        return repo.findAllByAnio(anio);
    }

}