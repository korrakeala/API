package ar.com.ada.mongo.netfly.entities;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Contenido
 */
@Document(collection = "Contenidos")
public class Contenido {

    public String nombre;
    public String genero;
    public Integer anio;

    public Contenido() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
}