package ar.com.ada.api.climate.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Temperatura
 */
@Entity
@Table(name = "temperatura")
public class Temperatura {

    @Id
    @Column(name = "temperatura_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int temperaturaId;
    @ManyToOne
    @JoinColumn(name = "pais_id", referencedColumnName = "pais_id")
    Pais pais;
    int anio;
    double grados;

    public int getTemperaturaId() {
        return temperaturaId;
    }

    public void setTemperaturaId(int temperaturaId) {
        this.temperaturaId = temperaturaId;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getGrados() {
        return grados;
    }

    public void setGrados(double grados) {
        this.grados = grados;
    }

    public Temperatura() {
    }

    
}