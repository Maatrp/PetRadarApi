package com.api.petradar.placeimages;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de repositorio para acceder a la colección de imágenes asociadas a los lugares en la base de datos MongoDB.
 */
@Repository
public interface PlaceImagesRepository extends MongoRepository<PlaceImages, String> {

}
