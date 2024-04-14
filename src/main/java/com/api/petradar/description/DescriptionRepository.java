package com.api.petradar.description;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DescriptionRepository extends MongoRepository<Description, String> {
}
