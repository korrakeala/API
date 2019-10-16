package ar.com.ada.api.simulacro.services;

import java.util.*;

import org.springframework.beans.factory.annotation.*;

import ar.com.ada.api.simulacro.entities.*;
import ar.com.ada.api.simulacro.exceptions.CategoriaException;
import ar.com.ada.api.simulacro.repos.CategoriaRepository;

/**
 * CategoriaService
 */
public class CategoriaService {

    @Autowired
    CategoriaRepository repo;

    @Autowired
    EmpleadoService es;

    public int crearCategoria(String nombre, double sueldoBase){
        Categoria c = new Categoria();
        c.setNombre(nombre);
        c.setSueldoBase(sueldoBase);
        repo.save(c);
        return c.getId();
    }

    public List<Categoria> listarCategorias(){ //SIN empleados????
        return repo.findAll();

    }

    public Categoria buscarPorId(int id) {

        Optional<Categoria> c = repo.findById(id);

        if (c.isPresent()) {
            return c.get();
        }
        return null;
    }

    public List<Empleado> getEmpleadosCategoriaNombre(String nombreCategoria) throws CategoriaException {
        int id;
        for (Categoria c : repo.findAll()) {
            if (c.nombre.equals(nombreCategoria)){
                id = c.getId();
                Categoria cat = buscarPorId(id);
                return cat.getEmpleados();
            }
        }
        throw new CategoriaException("No se encuentra una categoría con ese nombre.");
    }

    public List<Empleado> getEmpleadosCategoriaId(int id) throws CategoriaException {
        for (Categoria c : repo.findAll()) {
            if (c.getId() == id){
                Categoria cat = buscarPorId(id);
                return cat.getEmpleados();
            }
        }
        throw new CategoriaException("No se encuentra una categoría con ese id.");
    }


}