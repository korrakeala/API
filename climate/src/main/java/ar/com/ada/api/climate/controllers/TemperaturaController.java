package ar.com.ada.api.climate.controllers;

import java.util.ArrayList;
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
import ar.com.ada.api.climate.models.response.ListaTempAnioResponse;
import ar.com.ada.api.climate.models.response.TempMaxResponse;
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

    @GetMapping("/temperaturas/anios/{anio}") //falta formatear el json
    public List<ListaTempAnioResponse> getTemperaturasPorAnio(@PathVariable int anio){
        List<ListaTempAnioResponse> lr = new ArrayList<ListaTempAnioResponse>();
        ListaTempAnioResponse r = new ListaTempAnioResponse();
        List<Temperatura> lt = ts.listarTemperaturasPorAnio(anio);

        for (Temperatura t : lt) {
            r.nombrePais = t.getPais().getNombre();
            r.grados = t.getGrados();
            lr.add(r);
        }

        return lr;
    }

    @GetMapping("/temperaturas/maximas/{codigoPais}")
    public TempMaxResponse getTemperaturaMaximaPais(@PathVariable int codigoPais){
        TempMaxResponse r = new TempMaxResponse();
        
        Temperatura t = ts.buscarTemperaturaMaximaPais(codigoPais);
        
        r.nombrePais = t.getPais().getNombre();
        r.temperaturaMaxima = t.getGrados();
        r.anio = t.getAnio();
        return r;
        
    }

}