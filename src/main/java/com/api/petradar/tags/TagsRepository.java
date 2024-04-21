package com.api.petradar.tags;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends MongoRepository<Tags, String> {
}
