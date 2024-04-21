package com.api.petradar.restrictions;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictionsRepository extends MongoRepository<Restrictions, String> {
}
