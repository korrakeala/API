package ar.com.ada.api.simulacro.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.simulacro.entities.Categoria;
import ar.com.ada.api.simulacro.entities.Empleado;
import ar.com.ada.api.simulacro.models.request.EmpRequest;
import ar.com.ada.api.simulacro.models.response.EmpResponse;
import ar.com.ada.api.simulacro.services.CategoriaService;
import ar.com.ada.api.simulacro.services.EmpleadoService;

/**
 * EmpleadoController
 */
@RestController
public class EmpleadoController {

    @Autowired
    EmpleadoService es;

    @Autowired
    CategoriaService cs;

    @PostMapping("/empleados")
    public EmpResponse altaEmpleado(@RequestBody EmpRequest req){
        EmpResponse r = new EmpResponse();

        int empleadoCreadoId = es.crearEmpleado(req.nombre, req.edad, req.categoriaId);

        r.isOk = true;
        r.message = "Empleado " + req.nombre + " creado con éxito.";
        r.empleadoId = empleadoCreadoId;
        return r;
        
    }

    @GetMapping("/empleados")
    public List<Empleado> getEmpleados()
    {
        List<Empleado> le = es.listarEmpleados();
        
        return le;
    }

    @GetMapping("/empleados/{id}")
    public Empleado getEmpleado(@PathVariable int id){

        Empleado e = es.buscarPorId(id);
        return e;
    }

    @GetMapping("/empleados/categorias/{catId}")
    public List<Empleado> getEmpleadosCategoria(@PathVariable int catId){
        Categoria c = cs.buscarPorId(catId);
        List<Empleado> le = c.getEmpleados();
        return le;
    }

    @PutMapping("/empleados/{id}")
    public EmpResponse updateEmpleado(@PathVariable int id, @RequestBody EmpRequest req){
        EmpResponse r = new EmpResponse();

        Empleado e = es.updateEmpleado(id, req.nombre, req.edad, req.categoriaId);

        r.isOk = true;
        r.message = "Empleado " + req.nombre + " actualizado con éxito.";
        r.empleadoId = e.getId();
        return r;
    }

    @PutMapping("/empleados/{id}/sueldos")
    public EmpResponse updateSueldo(@PathVariable int id, @RequestBody EmpRequest req){
        EmpResponse r = new EmpResponse();

        Empleado e = es.updateSueldo(id, req.sueldo);

        r.isOk = true;
        r.message = "Empleado " + req.nombre + " actualizado con éxito.";
        r.empleadoId = e.getId();
        return r;
    }

    @DeleteMapping("/empleados/{id}")
    public EmpResponse bajaEmpleado(@PathVariable int id){
        EmpResponse r = new EmpResponse();

        Empleado e = es.bajaEmpleado(id);

        r.isOk = true;
        r.message = "Estado de empleado " + e.nombre + " modificado con éxito.";
        r.empleadoId = e.getId();
        return r;
    }
}