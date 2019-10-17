package ar.com.ada.api.climate.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.climate.entities.Pais;
import ar.com.ada.api.climate.models.request.PaisNombreRequest;
import ar.com.ada.api.climate.models.request.PaisRequest;
import ar.com.ada.api.climate.models.response.PaisResponse;
import ar.com.ada.api.climate.services.PaisService;
import ar.com.ada.api.climate.services.TemperaturaService;

/**
 * PaisController
 */
@RestController
public class PaisController {

    @Autowired
    TemperaturaService ts;

    @Autowired
    PaisService ps;

    @PostMapping("/paises")
    public PaisResponse postMethodName(@RequestBody PaisRequest req) {
        PaisResponse r = new PaisResponse();
        
        int codigoPais = ps.altaPais(req.codigoPais, req.nombre);

        r.isOk = true;
        r.message = "Pais " + req.nombre + " creado exitosamente.";
        r.codigoPais = codigoPais;
        return r;
    }

    @GetMapping("/paises")
    public List<Pais> getPaises()
    {
        List<Pais> lp = ps.listarPaises();
        
        return lp;
    }

    @GetMapping("/paises/{id}")
    public Pais getPais(@PathVariable int id){

        Pais p = ps.buscarPorId(id);
        return p;
    }

    @PutMapping("/paises/{id}") //crear responses y requests específicos
    public PaisResponse updatePais(@PathVariable int id, @RequestBody PaisNombreRequest req){
        PaisResponse r = new PaisResponse();

        Pais p = ps.updatePais(id, req.nombre);

        r.isOk = true;
        r.message = "Pais " + req.nombre + " actualizado con éxito.";
        r.codigoPais = p.getCodigoPais();
        return r;
    }
}