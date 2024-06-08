package com.api.petradar.description;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Repositorio para acceder a las descripciones de lugares en la base de datos.
 */
public interface DescriptionRepository extends MongoRepository<Description, String> {

    /**
     * Busca una descripción de mascota por su identificador de lugar.
     *
     * @param placeId El identificador del lugar asociado a la descripción de la mascota.
     * @return La descripción de la mascota encontrada, o null si no se encuentra ninguna descripción para el lugar especificado.
     */
    @Query(value = "{'id_place' : ?0}")
    Description findByPlaceId(String placeId);
}
