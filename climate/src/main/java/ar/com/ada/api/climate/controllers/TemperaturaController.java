package ar.com.ada.api.climate.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.climate.entities.Temperatura;
import ar.com.ada.api.climate.exceptions.TemperaturaExistenteException;
import ar.com.ada.api.climate.models.request.TemperaturaRegRequest;
import ar.com.ada.api.climate.models.response.TemperaturaBajaResponse;
import ar.com.ada.api.climate.models.response.TemperaturaRegResponse;
import ar.com.ada.api.climate.services.PaisService;
import ar.com.ada.api.climate.services.TemperaturaService;

/**
 * TemperaturaController
 */
@RestController
public class TemperaturaController {

    @Autowired
    TemperaturaService ts;

    @Autowired
    PaisService ps;

    @PostMapping("/temperaturas")
    public TemperaturaRegResponse postTemperatura(@RequestBody TemperaturaRegRequest req)
            throws TemperaturaExistenteException {
        TemperaturaRegResponse r = new TemperaturaRegResponse();
        
        Temperatura t= ts.altaTemperatura(req.codigoPais, req.anio, req.grados);

        r.id = t.getTemperaturaId();
        return r;
    }

    @GetMapping("/temperaturas/paises/{codigoPais}")
    public List<Temperatura> listarTemperaturasPorPais(@PathVariable int codigoPais){
        List<Temperatura> lt = ps.getTemperaturasPorPais(codigoPais);
        return lt;
    }

    @DeleteMapping("/temperaturas/{id}")
    public TemperaturaBajaResponse bajaTemperatura(@PathVariable int id){
        TemperaturaBajaResponse r = new TemperaturaBajaResponse();

        ts.bajaTemperatura(id);

        r.isOk = true;
        r.message = "Temperatura eliminada con éxito.";
        r.id = id;
        return r;
    }

    
}