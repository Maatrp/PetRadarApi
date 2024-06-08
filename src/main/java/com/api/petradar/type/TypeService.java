package com.api.petradar.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar los tipos de los lugares.
 */
@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    /**
     * Recupera todas los tipos de los lugares.
     *
     * @return Una lista de todos los tipos de los lugares.
     */
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }


}
