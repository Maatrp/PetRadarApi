package com.api.petradar.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/list")
    public ResponseEntity<List<Type>> getAllTags() {
        try {
            List<Type> typeList = typeService.getAllTypes();
            return new ResponseEntity<>(typeList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
