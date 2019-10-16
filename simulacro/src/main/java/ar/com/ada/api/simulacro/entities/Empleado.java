package ar.com.ada.api.simulacro.entities;

import java.util.Date;

import javax.persistence.*;

/**
 * Empleado
 */
@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @Column(name = "empleado_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String nombre;
    public int edad;
    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "categoria_id")
    public String categoria;
    public double sueldo;
    public String estado; //"Activo" o "Baja"
    @Column(name = "fecha_alta")
    public Date fechaAlta;
    @Column(name = "fecha_baja")
    public Date fechaBaja;

    public int getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Empleado() {
    }
}