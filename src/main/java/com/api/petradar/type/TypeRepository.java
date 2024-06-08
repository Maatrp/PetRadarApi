package com.api.petradar.type;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para acceder a los tipos de los lugares para mascotas en la base de datos MongoDB.
 */
@Repository
public interface TypeRepository extends MongoRepository<Type, String> {
}
