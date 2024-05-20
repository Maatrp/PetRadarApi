package com.api.petradar.place;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlaceRepository  extends MongoRepository<Place, String> {

    @Query(value = "{ 'geo' : { $nearSphere : { $geometry : { type : 'Point' , coordinates : [ ?0 , ?1 ] } , $maxDistance : ?2 }}, 'status' : 'AC'}")    List<Place> findNearPlaces(double longitude, double latitude, double maxDistanceInRadians);

    @Query("{ 'status' : 'AC' }")
    List<Place> findAcceptedPlaces();

    @Query("{ 'status' : 'PEND' }")
    List<Place> findPendingPlaces();

    @ExistsQuery(value = "{ 'name_place' : ?0, 'zip' : ?1 }")
    boolean existsByPlaceNameAndZip(String placeName, String zip);

    @Query(value = "{'_id' : ?0}")
    Place findByCustomId(String placeId);

}