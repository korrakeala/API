package ar.com.ada.mongo.netfly.entities;

import java.util.*;

import ar.com.ada.mongo.netfly.exceptions.ContenidoInexistenteException;

/**
 * Temporada
 */
public class Temporada {

    public Integer nroTemporada;
    public List<Episodio> episodios = new ArrayList<Episodio>();

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        this.episodios = episodios;
    }

    public Temporada() {
    }

    public Episodio getEpisodioNro(int nro) throws ContenidoInexistenteException {
        
        for (Episodio epi : this.episodios) {
            if (nro == epi.nroEpisodio) {
                return epi;
            }
        }
        throw new ContenidoInexistenteException("Episodio inexistente.");
    }

    public Integer getNroTemporada() {
        return nroTemporada;
    }

    public void setNroTemporada(Integer nroTemporada) {
        this.nroTemporada = nroTemporada;
    }
}