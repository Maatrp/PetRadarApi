package com.api.petradar.place;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlaceRepository  extends MongoRepository<Place, String> {

    @Query(value = "{ 'location' : { $nearSphere : { $geometry : { type : 'Point' , coordinates : [ ?0 , ?1 ] } , $maxDistance : ?2 }}}")
    List<Place> findNearPlaces(double longitude, double latitude, double maxDistanceInRadians);
    @ExistsQuery(value = "{ 'name_place' : ?0, 'zip' : ?1 }")
    boolean existsByPlaceNameAndZip(String placeName, String zip);
}