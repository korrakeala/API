package ar.com.ada.api.simulacro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.simulacro.entities.Empleado;

/**
 * EmpleadoRepository
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    
}