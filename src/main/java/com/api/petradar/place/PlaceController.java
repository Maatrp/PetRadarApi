package com.api.petradar.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @PreAuthorize("permitAll")
    @PostMapping("/list")
    public ResponseEntity<List<PlaceBase>> getRequestFilter(@RequestBody PlaceFilter placeFilter, @RequestParam Optional<String> userId) {
        try {
            List<PlaceBase> placeBases = placeService.getAllPlaces(placeFilter, userId.orElse(""));
            return new ResponseEntity<>(placeBases, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("permitAll")
    @GetMapping("/card/{id}")
    public ResponseEntity<Place> getPlaceById(@PathVariable String id, @RequestParam Optional<String> userId) {
        Place place = placeService.getPlaceById(id, userId.orElse(""));
        return new ResponseEntity<>(place, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE_PLACE')")
    @PostMapping("/create/{email}")
    public ResponseEntity<String> createPlace(@PathVariable String email, @RequestBody Place place) {
        try {
            boolean placeCreated = placeService.createPlace(email, place);

            if (placeCreated) {
                return ResponseEntity.status(HttpStatus.OK).body("Nuevo espacio creado.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Este lugar ya existe.");
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se ha podido crear el lugar.");
        }
    }
}