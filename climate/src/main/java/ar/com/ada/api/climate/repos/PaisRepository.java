package ar.com.ada.api.climate.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.climate.entities.Pais;

/**
 * CategoriaRepository
 */
public interface PaisRepository extends JpaRepository<Pais, Integer>{

    
}