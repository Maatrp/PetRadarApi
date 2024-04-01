package com.api.petradar.favorites;


import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository extends MongoRepository<Favorite, String> {

    boolean existsByUserIdAndPlaceId(String userId, String placeId);

    @DeleteQuery(value = "{ 'userId' : ?0, 'placeId': ?1 }")
    void deleteByUserIdAndPlaceId(String userId, String placeId);

    @Query(value = "{ 'userId' : ?0 }")
    List<Favorite> findAllByUserId(String userId);
}
