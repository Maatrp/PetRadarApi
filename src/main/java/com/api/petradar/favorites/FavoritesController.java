package com.api.petradar.favorites;

import com.api.petradar.place.PlaceBase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las operaciones relacionadas con los lugares favoritos.
 */
@RestController
@RequestMapping(path = "/favorites")
public class FavoritesController {
    @Autowired
    private FavoritesService favoritesService;

    /**
     * Añade un lugar a la lista de favoritos de un usuario.
     *
     * @param userId El ID del usuario.
     * @param placeId El ID del lugar que se va a añadir a favoritos.
     * @return ResponseEntity con un mensaje indicando si se ha añadido correctamente el favorito o no.
     */
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('FAVORITE_PLACE')")
    @PutMapping("/add/{userId}/{placeId}")
    public ResponseEntity<String> favoriteAdd(@PathVariable String userId, @PathVariable String placeId) {
        try {
            boolean favoriteCreated = favoritesService.favoriteAdd(userId, placeId);

            if (favoriteCreated) {
                return ResponseEntity.status(HttpStatus.OK).body("Favorito añadido");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No se ha podido añadir a la lista de favoritos.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se ha podido añadir a la lista de favoritos");
        }
    }

    /**
     * Elimina un lugar de la lista de favoritos de un usuario.
     *
     * @param userId El ID del usuario.
     * @param placeId El ID del lugar que se va a eliminar de favoritos.
     * @return ResponseEntity con un mensaje indicando si se ha eliminado correctamente el favorito o no.
     */
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('FAVORITE_PLACE')")
    @DeleteMapping("/remove/{userId}/{placeId}")
    public ResponseEntity<String> favoriteRemove(@PathVariable String userId, @PathVariable String placeId) {
        try {
            boolean favoriteRemoved = favoritesService.favoriteRemove(userId, placeId);
            if (favoriteRemoved) {
                return new ResponseEntity<>("Favorito eliminado", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se ha podido eliminar", HttpStatus.CONFLICT);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("No se ha podido añadir a la lista de favoritos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene la lista de favoritos de un usuario.
     *
     * @param userId El ID del usuario.
     * @return ResponseEntity con la lista de lugares favoritos del usuario.
     */
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('FAVORITE_PLACE')")
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<PlaceBase>> getFavoritesByUser(@PathVariable String userId) {
        try {
            List<PlaceBase> placeList = favoritesService.getFavoritesByUser(userId);

            return new ResponseEntity<>(placeList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}