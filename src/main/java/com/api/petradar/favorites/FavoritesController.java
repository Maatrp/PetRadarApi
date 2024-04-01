package com.api.petradar.favorites;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/favorites")
public class FavoritesController {
    @Autowired
    private FavoritesService favoritesService;
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

    @PreAuthorize("hasAuthority('FAVORITE_PLACE')")
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<Favorite>> getFavoritesByUser(@PathVariable String userId) {
        try {
            List<Favorite> favoritesList = favoritesService.getFavoritesByUser(userId);
            return new ResponseEntity<>(favoritesList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}