package ar.com.ada.mongo.netfly.entities;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ar.com.ada.mongo.netfly.exceptions.ContenidoInexistenteException;
/**
 * Serie
 */
@Document(collection = "Series")
public class Serie extends Contenido {

    @Id
    private ObjectId _id;

    public List<Temporada> temporadas = new ArrayList<Temporada>();

    public Serie() {
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(List<Temporada> temporadas) {
        this.temporadas = temporadas;
    }

    public Temporada getTemporada(Integer nroTemp) throws ContenidoInexistenteException {
        for (Temporada t : this.temporadas) {
            if (nroTemp.equals(t.getNroTemporada())) {
                return t;
            }
        }
        throw new ContenidoInexistenteException("Temporada inexistente.");
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
}