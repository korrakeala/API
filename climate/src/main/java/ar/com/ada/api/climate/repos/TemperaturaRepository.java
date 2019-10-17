package ar.com.ada.api.climate.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.climate.entities.Temperatura;

/**
 * CategoriaRepository
 */
public interface TemperaturaRepository extends JpaRepository<Temperatura, Integer>{

    List<Temperatura> findAllByAnio(int anio);
}