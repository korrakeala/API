package ar.com.ada.api.simulacro.services;

import java.util.*;

import org.springframework.beans.factory.annotation.*;

import ar.com.ada.api.simulacro.entities.*;
import ar.com.ada.api.simulacro.repos.EmpleadoRepository;

/**
 * EmpleadoService
 */
public class EmpleadoService {

    @Autowired
    EmpleadoRepository repo;

    @Autowired
    CategoriaService cs;

    public int crearEmpleado(String nombre, int edad, int categoriaId) {
        Empleado e = new Empleado();
        e.setNombre(nombre);
        e.setEdad(edad);
        Categoria c = cs.buscarPorId(categoriaId);
        e.setCategoria(c.getNombre());
        e.setSueldo(c.getSueldoBase());
        Date f = new Date();
        e.setFechaAlta(f);
        e.setEstado("Activo");
        repo.save(e);
        return e.getId();
    }

    public List<Empleado> listarEmpleados() { // CON info categoria
        return repo.findAll();

    }

    public Empleado buscarPorId(int id) {

        Optional<Empleado> e = repo.findById(id);

        if (e.isPresent()) {
            return e.get();
        }
        return null;
    }

    public Empleado updateEmpleado(int id, String nombre, int edad, int categoriaId) { // excepto sueldo y estado
        Empleado e = this.buscarPorId(id);
        e.setNombre(nombre);
        e.setEdad(edad);
        Categoria c = cs.buscarPorId(categoriaId);
        e.setCategoria(c.getNombre());
        repo.save(e);
        return e;
    }

    public Empleado updateSueldo(int id, double sueldo) {
        Empleado e = this.buscarPorId(id);
        e.setSueldo(sueldo);
        repo.save(e);
        return e;
    }

    public Empleado bajaEmpleado(int id) { // da de baja un empleadoo poniendo el campo "estado" en "baja" y la fecha de
                                       // baja en día actual
        Empleado e = this.buscarPorId(id);
        e.setEstado("Baja");
        Date f = new Date();
        e.setFechaBaja(f);
        repo.save(e);
        return e;

    }

}