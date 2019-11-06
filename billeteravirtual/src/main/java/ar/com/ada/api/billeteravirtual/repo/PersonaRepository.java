package ar.com.ada.api.billeteravirtual.repo;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.billeteravirtual.entities.Persona;

/**
 * PersonaRepository
 */
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    
    Persona findByNombre(String nombrePersona);

    Persona findByDni(String dni);

    Persona findByEmail(String email);

    List<Persona> findAllByNombreContiene(String nombre); //namedQuery en Persona

    List<Persona> findAllByNombreAndDNI(String nombre, String dni); //namedQuery en Persona

    @Query("select p from Persona p order by nombre")
    List<Persona> findAllOrderByNombre();

    @Query("SELECT p FROM Persona p WHERE p.dni = :dni")
    List<Persona> findByDniVersion2(@Param("dni") String descripcion);

    //@Query("SELECT p FROM Persona p WHERE p.dni like CONCAT('%', dni)")
    List<Persona> findByDniUltimosDig(String descripcion);

}