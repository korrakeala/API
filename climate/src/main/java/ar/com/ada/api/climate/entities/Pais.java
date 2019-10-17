package ar.com.ada.api.climate.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Pais
 */
@Entity
@Table(name = "pais")
public class Pais {

    @Id
    @Column(name = "pais_id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    int codigoPais;
    String nombre;
    @JsonIgnore
    @OneToMany (mappedBy = "pais", cascade = CascadeType.ALL)
    List<Temperatura> temperaturas;

    public int getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(int codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Temperatura> getTemperaturas() {
        return temperaturas;
    }

    public void setTemperaturas(List<Temperatura> temperaturas) {
        this.temperaturas = temperaturas;
    }

    public Pais(int codigoPais, String nombre) {
        this.codigoPais = codigoPais;
        this.nombre = nombre;
    }

    public Pais() {
    }

    
}