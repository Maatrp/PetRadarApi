package com.api.petradar.favorites;

import com.api.petradar.place.Place;
import com.api.petradar.place.PlaceBase;
import com.api.petradar.place.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servicio para gestionar las operaciones relacionadas con los lugares favoritos.
 */
@Service
public class FavoritesService {
    @Autowired
    private FavoritesRepository favoritesRepository;

    @Autowired
    private PlaceRepository placeRepository;

    /**
     * Añade un lugar a la lista de favoritos de un usuario.
     *
     * @param userId El ID del usuario.
     * @param placeId El ID del lugar que se va a añadir a favoritos.
     * @return true si el lugar se añadió correctamente a la lista de favoritos, false de lo contrario.
     */
    public boolean favoriteAdd(String userId, String placeId) {
        boolean added = false;
        try {
            if (!favoritesRepository.existsByUserIdAndPlaceId(userId, placeId)) {

                Favorite favorite = new Favorite();
                favorite.setPlaceId(placeId);
                favorite.setUserId(userId);
                favorite.setTimeCreated(new Date(System.currentTimeMillis()));
                favoritesRepository.save(favorite);
                added = true;
            }
        } catch (Exception e) {
            System.out.println("favoriteAdd: " + e);

        }
        return added;
    }

    /**
     * Elimina un lugar de la lista de favoritos de un usuario.
     *
     * @param userId El ID del usuario.
     * @param placeId El ID del lugar que se va a eliminar de favoritos.
     * @return true si el lugar se eliminó correctamente de la lista de favoritos, false de lo contrario.
     */
    public boolean favoriteRemove(String userId, String placeId) {
        boolean removed = false;
        try {
            favoritesRepository.deleteByUserIdAndPlaceId(userId, placeId);
            removed = true;
        } catch (Exception e) {
            System.out.println("favoriteRemove: " + e);
        }
        return removed;
    }

    /**
     * Obtiene la lista de lugares favoritos de un usuario.
     *
     * @param userId El ID del usuario.
     * @return Una lista de lugares favoritos del usuario.
     */
    public List<PlaceBase> getFavoritesByUser(String userId) {

        try {
            List<Favorite> favoritesList = favoritesRepository.findAllByUserId(userId);
            List<PlaceBase> placeBaseList = new ArrayList<>();

            for (Favorite favorite : favoritesList) {
                Place place = placeRepository.findByCustomId(favorite.getPlaceId());
                if (place != null && place.getStatus().equals("AC")) {
                    PlaceBase placeBase = new PlaceBase(
                            place.getId(),
                            place.getName(),
                            place.getType(),
                            place.getGeolocation().getCoordinates()[0],
                            place.getGeolocation().getCoordinates()[1],
                            true);
                    placeBaseList.add(placeBase);
                }
            }

            return placeBaseList;

        } catch (Exception e) {
            System.out.println("getFavoritesByUser: " + e);
            return new ArrayList<>();

        }
    }
}