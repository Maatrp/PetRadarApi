package com.api.petradar.restrictions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestrictionsService {
    @Autowired
    private RestrictionsRepository restrictionsRepository;

    public List<Restrictions> getAllRestrictions() {
        return restrictionsRepository.findAll();
    }
}
