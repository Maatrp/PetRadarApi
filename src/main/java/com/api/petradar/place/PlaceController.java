package com.api.petradar.place;

import com.api.petradar.placeimages.PlaceImage;
import com.api.petradar.placeimages.UploadPlaceImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService;
    @Autowired
    private UploadPlaceImageService uploadPlaceImageService;
    private String url;

    @PostMapping("/list")
    public ResponseEntity<List<PlaceBase>> getRequestFilter(@RequestBody PlaceFilter placeFilter, @RequestParam Optional<String> userId) {
        try {
            List<PlaceBase> placeBases = placeService.getAllPlaces(placeFilter, userId.orElse(""));
            return new ResponseEntity<>(placeBases, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/card/{id}")
    public ResponseEntity<Place> getPlaceById(@PathVariable String id, @RequestParam Optional<String> userId) {
        Place place = placeService.getPlaceById(id, userId.orElse(""));
        return new ResponseEntity<>(place, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE_PLACE')")
    @PostMapping("/create/{idUser}")
    public ResponseEntity<String> createPlace(@PathVariable String idUser,
                                              @RequestPart("placeData") String placeData,
                                              @RequestPart("file") MultipartFile file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PlaceDto placeDto = mapper.readValue(placeData, PlaceDto.class);

            if (file != null) {
                String url = uploadPlaceImageService.uploadImage(file);
                if (url != null && !url.isEmpty()) {
                    PlaceImage placeImage = new PlaceImage(url);
                    PlaceImage[] placeImages = new PlaceImage[1];
                    placeImages[0] = placeImage;
                    placeDto.setPlaceImages(placeImages);
                }
            }

            boolean placeCreated = placeService.createPlace(idUser, placeDto);

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

    @PreAuthorize("hasAuthority('UPDATE_STATUS_PLACE')")
    @GetMapping("/pending-places/{userId}")
    public ResponseEntity<List<PlaceBase>> getPendingPlaces(@PathVariable String userId) {
        try {
            List<PlaceBase> pendingPlaces = placeService.getPendingPlaces(userId);

            return new ResponseEntity<>(pendingPlaces, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasAuthority('UPDATE_STATUS_PLACE')")
    @PutMapping("/update-status/{placeId}/{status}")
    public ResponseEntity<String> updateStatusPlace(@PathVariable String placeId, @PathVariable String status) {
        try {
            boolean placeStatusUpdated = placeService.updateStatusPlace(placeId, status);

            if (placeStatusUpdated) {
                return ResponseEntity.status(HttpStatus.OK).body("Estado del espacio actualizado.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No se ha podido actualizar el estado.");
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se ha podido modificar el estado.");
        }
    }

    @PreAuthorize("hasAuthority('UPDATE_STATUS_PLACE')")
    @PutMapping("/update-all-status/{status}")
    public ResponseEntity<String> updateAllStatusPlaces(@RequestBody List<String> placeIdList, @PathVariable String status) {
        try {
            boolean placesStatusUpdated = placeService.updateAllStatusPlaces(placeIdList, status);

            if (placesStatusUpdated) {
                return ResponseEntity.status(HttpStatus.OK).body("Los estados han sido actualizados.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Los estados no se han podido actualizar.");
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Los estados no se ha podido modificar.");
        }
    }
}