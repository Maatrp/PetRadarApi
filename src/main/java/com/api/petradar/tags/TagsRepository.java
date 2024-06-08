package com.api.petradar.tags;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para acceder a los tags de los lugares para mascotas en la base de datos MongoDB.
 */
@Repository
public interface TagsRepository extends MongoRepository<Tags, String> {
}
