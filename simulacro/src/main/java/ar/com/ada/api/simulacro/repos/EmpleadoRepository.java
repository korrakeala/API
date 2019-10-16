package ar.com.ada.api.simulacro.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.simulacro.entities.*;

/**
 * EmpleadoRepository
 */
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    
}