package ar.com.ada.mongo.netfly.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.mongo.netfly.entities.Peli;
import ar.com.ada.mongo.netfly.exceptions.ContenidoInexistenteException;
import ar.com.ada.mongo.netfly.models.response.PostPeliResponse;
import ar.com.ada.mongo.netfly.services.PeliService;

/**
 * PeliController
 */
@RestController
public class PeliController {

    @Autowired
    PeliService ps;

    @PostMapping("/pelis")
    public PostPeliResponse postPeli(@RequestBody Peli peliReq) {
        PostPeliResponse r = new PostPeliResponse();

        ps.grabar(peliReq);

        r.isOk = true;
        r.message = "Peli creada con éxito";
        r.peliId = peliReq.get_id().toString();

        return r;
    }

    @PutMapping("/pelis/{idPeli}/")
    public PostPeliResponse putPeli(@PathVariable String idPeli, @RequestBody Peli peliReq) throws ContenidoInexistenteException {
        PostPeliResponse r = new PostPeliResponse();

        ps.modificarPeli(idPeli, peliReq);

        r.isOk = true;
        r.message = "Película modificada";
        r.peliId = peliReq.get_id().toString();
        return r;

    }
}