package ar.com.ada.mongo.netfly.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import ar.com.ada.mongo.netfly.entities.Usuario;

/**
 * UsuarioRepository
 */
public interface UsuarioRepository extends MongoRepository<Usuario, ObjectId> {

    Usuario findBy_id(ObjectId _id);    
    Usuario findByUserName(String userName);
    Usuario findByUserEmail(String email);
}