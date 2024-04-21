package com.api.petradar.tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/tags")
public class TagsContoller {

    @Autowired
    private TagsSevice tagsSevice;

    @GetMapping("/list")
    public ResponseEntity<List<Tags>> getAllTags() {
        try {
            List<Tags> tagsList = tagsSevice.getAllTags();
            return new ResponseEntity<>(tagsList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
