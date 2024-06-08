package com.api.petradar.place;

import com.api.petradar.placeimages.PlaceImage;
import com.api.petradar.placeimages.UploadPlaceImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las solicitudes relacionadas con los lugares.
 */
@RestController
@RequestMapping(path = "/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService;
    @Autowired
    private UploadPlaceImageService uploadPlaceImageService;

    /**
     * Maneja las solicitudes POST para obtener una lista de lugares filtrada.
     * @param placeFilter El filtro de lugar para aplicar.
     * @param userId El ID del usuario opcional.
     * @return ResponseEntity con una lista de PlaceBase y el estado HTTP correspondiente.
     */
    @PostMapping("/list")
    public ResponseEntity<List<PlaceBase>> getRequestFilter(@RequestBody PlaceFilter placeFilter, @RequestParam Optional<String> userId) {
        try {
            List<PlaceBase> placeBases = placeService.getAllPlaces(placeFilter, userId.orElse(""));
            return new ResponseEntity<>(placeBases, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Maneja las solicitudes GET para obtener información detallada de un lugar por su ID.
     * @param id El ID del lugar.
     * @param userId El ID del usuario opcional.
     * @return ResponseEntity con el lugar y el estado HTTP correspondiente.
     */
    @GetMapping("/card/{id}")
    public ResponseEntity<Place> getPlaceById(@PathVariable String id, @RequestParam Optional<String> userId) {
        Place place = placeService.getPlaceById(id, userId.orElse(""));
        return new ResponseEntity<>(place, HttpStatus.OK);
    }

    /**
     * Maneja las solicitudes POST para crear un nuevo lugar.
     * @param idUser El ID del usuario que crea el lugar.
     * @param placeData Los datos del lugar en formato JSON.
     * @param file El archivo de imagen del lugar (opcional).
     * @return ResponseEntity con un mensaje y el estado HTTP correspondiente.
     */
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('CREATE_PLACE')")
    @PostMapping("/create/{idUser}")
    public ResponseEntity<String> createPlace(@PathVariable String idUser,
                                              @RequestPart("placeData") String placeData,
                                              @RequestPart(name= "file", required = false) MultipartFile file ) {
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

    /**
     * Maneja las solicitudes GET para obtener una lista de lugares pendientes.
     * @param userId El ID del usuario que realiza la solicitud.
     * @return ResponseEntity con una lista de PlaceBase y el estado HTTP correspondiente.
     */
    @SecurityRequirement(name = "bearerAuth")
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

    /**
     * Maneja las solicitudes PUT para actualizar el estado de un lugar específico.
     * @param placeId El ID del lugar que se actualizará.
     * @param status El nuevo estado del lugar.
     * @return ResponseEntity con un mensaje y el estado HTTP correspondiente.
     */
    @SecurityRequirement(name = "bearerAuth")
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

    /**
     * Maneja las solicitudes PUT para actualizar el estado de varios lugares.
     * @param placeIdList La lista de IDs de lugares que se actualizarán.
     * @param status El nuevo estado de los lugares.
     * @return ResponseEntity con un mensaje y el estado HTTP correspondiente.
     */
    @SecurityRequirement(name = "bearerAuth")
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