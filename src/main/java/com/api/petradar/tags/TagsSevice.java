package com.api.petradar.tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar los tags de los lugares.
 */
@Service
public class TagsSevice {

    @Autowired
    private TagsRepository tagsRepository;

    /**
     * Recupera todas los tags de los lugares.
     *
     * @return Una lista de todos los tags de los lugares.
     */
    public List<Tags> getAllTags() {
        return tagsRepository.findAll();
    }


}
