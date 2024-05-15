package com.api.petradar.description;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface DescriptionRepository extends MongoRepository<Description, String> {
    @Query(value = "{'id_place' : ?0}")
    Description findByPlaceId(String placeId);
}
