package ar.com.ada.api.simulacro.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.simulacro.entities.Categoria;

/**
 * CategoriaRepository
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

    
}