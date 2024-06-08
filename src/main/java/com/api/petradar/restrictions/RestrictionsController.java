package com.api.petradar.restrictions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Controlador para gestionar las restricciones de los lugares.
 */
@RestController
@RequestMapping(path = "/restrictions")
public class RestrictionsController {

    @Autowired
    private RestrictionsService restrictionsService;

    /**
     * Obtiene todas las restricciones.
     *
     * @return ResponseEntity con la lista de restricciones o HttpStatus.NO_CONTENT si no hay restricciones.
     */
    @GetMapping("/list")
    public ResponseEntity<List<Restrictions>> getAllRestrictions() {
        try {
            List<Restrictions> restrictionsList = restrictionsService.getAllRestrictions();
            return new ResponseEntity<>(restrictionsList, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}