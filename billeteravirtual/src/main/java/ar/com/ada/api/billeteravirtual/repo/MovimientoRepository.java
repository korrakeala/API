package ar.com.ada.api.billeteravirtual.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.billeteravirtual.entities.Movimiento;

/**
 * MovimientoRepository
 */
@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    
}