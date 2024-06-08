package com.api.petradar.favorites;


import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para acceder a los lugares favoritos en la base de datos.
 */
@Repository
public interface FavoritesRepository extends MongoRepository<Favorite, String> {

    /**
     * Verifica si existe un lugar favorito para un usuario y un lugar específicos.
     *
     * @param userId El ID del usuario.
     * @param placeId El ID del lugar.
     * @return true si existe un favorito para el usuario y el lugar especificados, false de lo contrario.
     */
    boolean existsByUserIdAndPlaceId(String userId, String placeId);

    /**
     * Elimina un lugar favorito basado en el ID de usuario y el ID de lugar.
     *
     * @param userId El ID del usuario.
     * @param placeId El ID del lugar.
     */
    @DeleteQuery(value = "{ 'userId' : ?0, 'placeId': ?1 }")
    void deleteByUserIdAndPlaceId(String userId, String placeId);

    /**
     * Encuentra todos los lugares favoritos  para un usuario específico.
     *
     * @param userId El ID del usuario.
     * @return Una lista de favoritos de lugares para el usuario especificado.
     */
    @Query(value = "{ 'userId' : ?0 }")
    List<Favorite> findAllByUserId(String userId);
}
