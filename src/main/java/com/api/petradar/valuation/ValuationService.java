package com.api.petradar.valuation;

import com.api.petradar.place.Place;
import com.api.petradar.place.PlaceRepository;
import com.api.petradar.user.User;
import com.api.petradar.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servicio para gestionar las valoraciones de lugares.
 */
@Service
public class ValuationService {

    @Autowired
    private ValuationRepository valuationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    /**
     * Obtiene las valoraciones de un lugar por su ID, excluyendo las reportadas.
     *
     * @param placeId El ID del lugar.
     * @return Una lista de valoraciones para el lugar especificado.
     */
    public List<Valuation> getValuationsPlace(String placeId) {
        List<Valuation> placeValuations = new ArrayList<>();

        try {
            List<Valuation> allPlaceValuations = valuationRepository.findByPlaceId(placeId);

            if (!allPlaceValuations.isEmpty()) {
                for (Valuation valuation : allPlaceValuations) {
                    if (!valuation.isReported()) {
                        User user = userRepository.findUserById(valuation.getUserId());
                        valuation.setUserName(user.getUsername());
                        placeValuations.add(valuation);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("getValuesPlace: " + e);

        }
        return placeValuations;
    }

    /**
     * Crea una nueva valoración para un lugar.
     *
     * @param valuation La valoración a crear.
     * @return true si la valoración fue creada exitosamente, false en caso contrario.
     */
    public boolean createValuation(Valuation valuation) {
        boolean alreadyValuated = valuationRepository.alreadyValuated(valuation.getUserId(), valuation.getPlaceId());
        boolean isCreated = false;

        if (!alreadyValuated) {
            valuation.setTimeCreated(new Date(System.currentTimeMillis()));
            valuation.setReported(false);
            valuationRepository.save(valuation);

            Place place = placeRepository.findByCustomId(valuation.getPlaceId());
            place.setAverageRating(updateAverageRating(place.getId()));
            placeRepository.save(place);
            isCreated = true;


        } else {
            System.out.println("Ya has valorado este lugar");
        }

        return isCreated;

    }

    /**
     * Actualiza una valoración existente.
     *
     * @param valuation La valoración a actualizar.
     * @return true si la valoración fue actualizada exitosamente, false en caso contrario.
     */
    public boolean updateValuation(Valuation valuation) {
        boolean isUpdated = false;

        if (valuation != null) {
            valuationRepository.updateById(valuation.getId(), valuation);
            isUpdated = true;
        }

        return isUpdated;

    }

    /**
     * Elimina una valoración por su ID.
     *
     * @param id El ID de la valoración a eliminar.
     * @return true si la valoración fue eliminada exitosamente, false en caso contrario.
     */
    public boolean deleteValuation(String id) {
        boolean exists = valuationRepository.existsById(id);
        boolean isDeleted = false;

        if (exists) {
            valuationRepository.deleteById(id);
            isDeleted = true;
        }

        return isDeleted;
    }

    /**
     * Verifica si un usuario ya ha valorado un lugar específico.
     *
     * @param userId  El ID del usuario.
     * @param placeId El ID del lugar.
     * @return true si el usuario ya ha valorado el lugar, false en caso contrario.
     */
    public boolean alreadyValuated(String userId, String placeId) {
        return valuationRepository.alreadyValuated(userId, placeId);
    }

    /**
     * Actualiza la calificación promedio de un lugar.
     *
     * @param placeId El ID del lugar.
     * @return La calificación promedio actualizada.
     */
    private double updateAverageRating(String placeId) {
        List<Valuation> valuationList = valuationRepository.findByPlaceId(placeId);

        double averageRating = 0;
        double sum = 0;

        if (!valuationList.isEmpty()) {
            for (Valuation valuation : valuationList) {
                sum += valuation.getAverageRating();
            }
            averageRating = sum / valuationList.size();
        }

        return averageRating;
    }
}