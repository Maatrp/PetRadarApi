package com.api.petradar.valuation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar las valoraciones de los lugares.
 */
@RestController
@RequestMapping(path = "/valuations")
public class ValuationController {

    @Autowired
    private ValuationService valuationService;

    /**
     * Obtiene todas las valoraciones.
     *
     * @return ResponseEntity con la lista de valoraciones o HttpStatus.NO_CONTENT si no hay valoraciones.
     */
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('VALUATION_PLACE')")
    @GetMapping("/{placeId}")
    public ResponseEntity<List<Valuation>> getPlaces(@PathVariable String placeId) {
        try {
            List<Valuation> placeValuations = valuationService.getValuationsPlace(placeId);

            return new ResponseEntity<>(placeValuations, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Crea una nueva valoración para un lugar.
     *
     * @param valuation Objeto Valuation que representa la valoración a crear.
     * @return ResponseEntity con un mensaje indicando el resultado de la operación.
     */
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('VALUATION_PLACE')")
    @PostMapping("/create")
    public ResponseEntity<String> createValuation(@RequestBody Valuation valuation) {
        try {
            boolean valuationCreated = valuationService.createValuation(valuation);

            if (valuationCreated) {
                return ResponseEntity.status(HttpStatus.OK).body("Nueva valoración añadida.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya ha valorado este lugar.");
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se ha podido valorar el lugar.");
        }
    }

    /**
     * Modifica una valoración existente.
     *
     * @param valuation Objeto Valuation que representa la valoración a modificar.
     * @return ResponseEntity con un mensaje indicando el resultado de la operación.
     */
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('VALUATION_PLACE')")
    @PutMapping("/modify")
    public ResponseEntity<String> modifyValuation(@RequestBody Valuation valuation) {

        boolean modified = valuationService.updateValuation(valuation);

        if (modified) {
            return new ResponseEntity<>("Valoración modificada con exito", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("No se ha podido modificar la valoración.", HttpStatus.CONFLICT);
        }
    }

    /**
     * Elimina una valoración existente.
     *
     * @param id Identificador de la valoración a eliminar.
     * @return ResponseEntity con un mensaje indicando el resultado de la operación.
     */
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('VALUATION_PLACE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteValuation(@PathVariable String id) {

        boolean deleted = valuationService.deleteValuation(id);

        if (deleted) {
            return new ResponseEntity<>("Valoración eliminada con exito", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("No se ha podido eliminar la valoración.", HttpStatus.CONFLICT);

        }

    }

    /**
     * Verifica si un usuario ya ha valorado un lugar específico.
     *
     * @param userId  Identificador del usuario.
     * @param placeId Identificador del lugar.
     * @return ResponseEntity con un booleano indicando si el usuario ya ha valorado el lugar o HttpStatus.NO_CONTENT si ocurre un error.
     */
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('VALUATION_PLACE')")
    @GetMapping("/{userId}/{placeId}")
    public ResponseEntity<Boolean> getIsAlreadyValuated(@PathVariable String userId,@PathVariable String placeId) {
        try {
            Boolean alreadyValuated = valuationService.alreadyValuated(userId,placeId);

            return new ResponseEntity<>(alreadyValuated, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}