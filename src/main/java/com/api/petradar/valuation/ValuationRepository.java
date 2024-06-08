package com.api.petradar.valuation;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para realizar operaciones CRUD en la colección de Valuations.
 */
@Repository
public interface ValuationRepository extends MongoRepository<Valuation, String> {

    /**
     * Encuentra todas las valoraciones por el ID del lugar.
     *
     * @param placeId El ID del lugar.
     * @return Una lista de valoraciones para el lugar especificado.
     */
    @Query(value = "{'id_place' : ?0}")
    List<Valuation> findByPlaceId(String placeId);

    /**
     * Verifica si un usuario ya ha valorado un lugar específico.
     *
     * @param userId  El ID del usuario.
     * @param placeId El ID del lugar.
     * @return {@code true} si el usuario ya ha valorado el lugar, {@code false} en caso contrario.
     */
    @ExistsQuery(value = "{ 'id_user' : ?0, 'id_place' : ?1 }")
    boolean alreadyValuated(String userId, String placeId);

    /**
     * Encuentra una valoración por su ID.
     *
     * @param id El ID de la valoración.
     * @return La valoración con el ID especificado, o {@code null} si no se encuentra.
     */
    @Query("{'_id' : ?0}")
    Valuation findValuationById(String id);

    /**
     * Actualiza una valoración existente por su ID.
     *
     * @param id        El ID de la valoración a actualizar.
     * @param valuation La valoración con los nuevos datos.
     */
    default void updateById(String id, Valuation valuation) {
        Valuation existingValuation = findValuationById(id);
        if (existingValuation != null) {
            existingValuation.setCommentMessage(valuation.getCommentMessage());
            existingValuation.setAverageRating(valuation.getAverageRating());
            save(existingValuation);
        }
    }

    /**
     * Elimina una valoración por su ID.
     *
     * @param id El ID de la valoración a eliminar.
     */
    void deleteById(String id);

}
