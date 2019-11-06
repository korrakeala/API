package ar.com.ada.mongo.netfly.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import ar.com.ada.mongo.netfly.entities.Peli;

/**
 * PeliRepository
 */
public interface PeliRepository extends MongoRepository<Peli, ObjectId> {

    Peli findBy_id(ObjectId _id);
    Peli findByNombre(String nombre);
    Peli findByGenero(String genero);
}