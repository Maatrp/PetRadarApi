package com.api.petradar.place;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para realizar operaciones CRUD en la colección de lugares.
 */
@Repository
public interface PlaceRepository  extends MongoRepository<Place, String> {
    /**
     * Encuentra los lugares cercanos a una ubicación geográfica dada dentro de una distancia máxima.
     *
     * @param longitude            Longitud de la ubicación de referencia.
     * @param latitude             Latitud de la ubicación de referencia.
     * @param maxDistanceInRadians Distancia máxima en radianes dentro de la cual buscar lugares.
     * @return Lista de lugares cercanos dentro de la distancia especificada.
     */
    @Query(value = "{ 'geo' : { $nearSphere : { $geometry : { type : 'Point' , coordinates : [ ?0 , ?1 ] } , $maxDistance : ?2 }}, 'status' : 'AC'}")
    List<Place> findNearPlaces(double longitude, double latitude, double maxDistanceInRadians);

    /**
     * Encuentra todos los lugares aceptados.
     *
     * @return Lista de lugares aceptados.
     */
    @Query("{ 'status' : 'AC' }")
    List<Place> findAcceptedPlaces();


    /**
     * Encuentra todos los lugares pendientes.
     *
     * @return Lista de lugares pendientes.
     */
    @Query("{ 'status' : 'PEND' }")
    List<Place> findPendingPlaces();

    /**
     * Verifica si existe un lugar con un nombre y código postal dados.
     *
     * @param placeName Nombre del lugar a verificar.
     * @param zip       Código postal del lugar a verificar.
     * @return Verdadero si existe un lugar con el nombre y código postal dados, falso en caso contrario.
     */
    @ExistsQuery(value = "{ 'name_place' : ?0, 'zip' : ?1 }")
    boolean existsByPlaceNameAndZip(String placeName, String zip);

    /**
     * Encuentra un lugar por su identificador personalizado.
     *
     * @param placeId Identificador personalizado del lugar.
     * @return El lugar correspondiente al identificador personalizado especificado.
     */
    @Query(value = "{'_id' : ?0}")
    Place findByCustomId(String placeId);

}