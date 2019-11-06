package ar.com.ada.mongo.netfly.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import ar.com.ada.mongo.netfly.entities.Serie;

/**
 * SerieRepository
 */
public interface SerieRepository extends MongoRepository<Serie, ObjectId> {

    Serie findBy_id(ObjectId _id);
    Serie findByNombre(String nombre);
    Serie findByGenero(String genero);
}