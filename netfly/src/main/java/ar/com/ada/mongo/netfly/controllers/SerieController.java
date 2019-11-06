package ar.com.ada.mongo.netfly.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.mongo.netfly.entities.*;
import ar.com.ada.mongo.netfly.exceptions.ContenidoInexistenteException;
import ar.com.ada.mongo.netfly.models.response.*;
import ar.com.ada.mongo.netfly.models.response.PostTemporadaResponse;
import ar.com.ada.mongo.netfly.services.SerieService;

/**
 * SerieController
 */
@RestController
public class SerieController {

    @Autowired
    SerieService ss;

    @PostMapping("/series")
    public PostSerieResponse postSerie(@RequestBody Serie serieReq) {
        PostSerieResponse r = new PostSerieResponse();

        ss.grabar(serieReq);

        r.isOk = true;
        r.message = "Serie creada con éxito";
        r.serieId = serieReq.get_id().toString();

        return r;
    }

    @PostMapping("/series/{idSerie}/temporadas/")
    public PostTemporadaResponse postTemporada(@PathVariable String idSerie, @RequestBody Temporada tempReq)
            throws ContenidoInexistenteException {
        PostTemporadaResponse r = new PostTemporadaResponse();

        Serie s = ss.buscarPorId(idSerie);
        ss.agregarTemporada(s.getNombre(), tempReq);

        r.isOk = true;
        r.message = "Temporada generada con éxitoooeeaaggh.";
        r.nroTemp = tempReq.getNroTemporada();
        return r;
    }

    @PostMapping("/series/{idSerie}/temporadas/{nroTemp}/")
    public PostEpisodioResponse postEpisodio(@PathVariable String idSerie, @PathVariable Integer nroTemp,
            @RequestBody Episodio epiReq) throws ContenidoInexistenteException {
        PostEpisodioResponse r = new PostEpisodioResponse();

        Serie s = ss.buscarPorId(idSerie);
        ss.agregarEpisodio(s.getNombre(), nroTemp, epiReq);

        r.isOk = true;
        r.message = "Episodio agregadou";
        r.nroEpi = epiReq.getNroEpisodio();
        return r;
    }

    @PutMapping("/series/{idSerie}/temporadas/{nroTemp}/episodios/{nroEpisodio}/")
    public PostEpisodioResponse putEpisodio(@PathVariable String idSerie, @PathVariable Integer nroTemp, @PathVariable Integer nroEpisodio,
    @RequestBody Episodio epiReq) throws ContenidoInexistenteException {
        PostEpisodioResponse r = new PostEpisodioResponse();

        Serie s = ss.buscarPorId(idSerie);
        ss.modificarEpisodio(s.getNombre(), nroTemp, nroEpisodio, epiReq);

        r.isOk = true;
        r.message = "Episodio modificado";
        r.nroEpi = epiReq.getNroEpisodio();
        return r;

    }
}