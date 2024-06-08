package com.api.petradar.valuation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/valuations")
public class ValuationController {

    @Autowired
    private ValuationService valuationService;
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