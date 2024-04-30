package com.api.petradar.valuation;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValuationRepository extends MongoRepository<Valuation, String> {

    @Query(value = "{'id_place' : ?0}")
    List<Valuation> findByPlaceId(String placeId);

    @ExistsQuery(value = "{ 'id_user' : ?0, 'id_place' : ?1 }")
    boolean alreadyValuated(String userId, String placeId);

    @Query("{'_id' : ?0}")
    Valuation findValuationById(String id);

    default void updateById(String id, Valuation valuation) {
        Valuation existingValuation = findValuationById(id);
        if (existingValuation != null) {
            existingValuation.setCommentMessage(valuation.getCommentMessage());
            existingValuation.setAverageRating(valuation.getAverageRating());
            save(existingValuation);
        }
    }

    void deleteById(String id);

}
