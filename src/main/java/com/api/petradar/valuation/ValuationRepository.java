package com.api.petradar.valuation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValuationRepository extends MongoRepository<Valuation, String> {
}
