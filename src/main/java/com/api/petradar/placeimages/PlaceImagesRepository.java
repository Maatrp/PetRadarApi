package com.api.petradar.placeimages;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlaceImagesRepository extends MongoRepository<PlaceImages, String> {

}
