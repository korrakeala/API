package ar.com.ada.api.billeteravirtual.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.repo.PersonaRepository;
import ar.com.ada.api.billeteravirtual.entities.*;
/**
 * PersonaService
 */
@Service
public class PersonaService {

    @Autowired
    PersonaRepository repo;


    public void save(Persona p){
        repo.save(p);
    }

    public List<Persona> getPersonas() {

        return repo.findAll();
    }

    public Persona buscarPorNombre(String nombre) {

        return repo.findByNombre(nombre);
    }
 
    
    public Persona buscarPorDni(String dni) {

        return repo.findByDni(dni);
    }

    public List<Persona> buscarTodasPorDni(String dni) {

        return repo.findByDniVersion2(dni);

    }

    public List<Persona> buscarTodasPorDniUltimosDig(String descripcion) {

        return repo.findByDniUltimosDig(descripcion);

    }

    public Persona buscarPorId(int id) {

        Optional<Persona> p = repo.findById(id); //Optional - es una Persona, o es nada
        
        if (p.isPresent())
            return p.get();
        return null;
    }

    public Persona buscarPorEmail(String email){
        
        return repo.findByEmail(email);
    }

    public List<Persona> buscarPersonasOrdenadoPorNombre() {

        return repo.findAllOrderByNombre(); // namedQuery en Persona
    }
 
    public List<Persona> buscarTodosPorNombre(String nombre) {

        return repo.findAllByNombreContiene(nombre); // namedQuery en Persona
    }
    
}