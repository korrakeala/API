package ar.com.ada.api.simulacro.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.simulacro.entities.*;
import ar.com.ada.api.simulacro.models.request.AltaCatRequest;
import ar.com.ada.api.simulacro.models.response.AltaCatResponse;
import ar.com.ada.api.simulacro.services.CategoriaService;

/**
 * CategoriaController
 */
@RestController
public class CategoriaController {

    @Autowired
    CategoriaService cs;

    @PostMapping("/categorias")
    public AltaCatResponse altaCategoria(@RequestBody AltaCatRequest req){
        AltaCatResponse r = new AltaCatResponse();

        int categoriaCreadaId = cs.crearCategoria(req.nombre, req.sueldoBase);

        r.isOk = true;
        r.message = "Categoría " + req.nombre + " creada con éxito.";
        r.categoriaId = categoriaCreadaId;
        return r;
        
    }

    @GetMapping("/categorias")
    public List<Categoria> getCategorias()
    {
        List<Categoria> lc = cs.listarCategorias();
        
        return lc;
    }

    
}