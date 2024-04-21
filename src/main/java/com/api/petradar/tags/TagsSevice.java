package com.api.petradar.tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsSevice {

    @Autowired
    private TagsRepository tagsRepository;

    public List<Tags> getAllTags() {
        return tagsRepository.findAll();
    }


}
