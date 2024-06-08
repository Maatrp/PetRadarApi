package com.api.petradar.restrictions;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para acceder a las restricciones de los lugares para mascotas en la base de datos MongoDB.
 */
@Repository
public interface RestrictionsRepository extends MongoRepository<Restrictions, String> {
}
