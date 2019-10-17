package ar.com.ada.api.climate.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.climate.entities.Temperatura;

/**
 * CategoriaRepository
 */
public interface TemperaturaRepository extends JpaRepository<Temperatura, Integer>{

    
}