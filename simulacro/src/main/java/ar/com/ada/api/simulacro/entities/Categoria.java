package ar.com.ada.api.simulacro.entities;

import java.util.*;

import javax.persistence.*;

/**
 * Categoria
 */
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @Column(name = "categoria_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String nombre;
    @Column(name = "sueldo_base")
    public double sueldoBase;
    @OneToMany (mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Empleado> empleados = new ArrayList<Empleado>();

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(double sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public Categoria() {
    }
}