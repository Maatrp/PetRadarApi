package com.api.petradar.restrictions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar las restricciones de los lugares.
 */
@Service
public class RestrictionsService {
    @Autowired
    private RestrictionsRepository restrictionsRepository;

    /**
     * Recupera todas las restricciones de los lugares.
     *
     * @return Una lista de todas las restricciones de los lugares.
     */
    public List<Restrictions> getAllRestrictions() {
        return restrictionsRepository.findAll();
    }
}
