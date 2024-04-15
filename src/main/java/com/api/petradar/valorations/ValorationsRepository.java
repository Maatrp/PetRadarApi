package com.api.petradar.valorations;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValorationsRepository  extends MongoRepository<Valorations, String> {
}
