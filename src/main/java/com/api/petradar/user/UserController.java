package com.api.petradar.user;

import com.api.petradar.email.EmailService;
import jakarta.ws.rs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PreAuthorize("permitAll")
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user){

        try {
            boolean created = userService.createUser(user);

            if (created) {
                boolean isEmailSent = emailService.sendEmail(user.getEmail());
                if (isEmailSent) {
                    return new ResponseEntity<>("Usuario creado con exito", HttpStatus.CREATED);
                } else {
                    userService.deleteUserName(user.getUsername());
                    return new ResponseEntity<>("No se ha podido crear el usuario.", HttpStatus.NO_CONTENT);
                }
            } else {
                return new ResponseEntity<>("El usario ya existe en la base de datos.", HttpStatus.CONFLICT);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("No se ha podido crear el usuario.", HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("permitAll")
    @GetMapping("/validateEmail")
    public ResponseEntity<String> validateUser(@PathParam("email") String email, @PathParam("token") String token) {

        boolean isVerified = userService.emailVerified(email, token);

        if (isVerified) {
            return new ResponseEntity<>("Email validado con exito", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("No se ha podido comprobar el email.", HttpStatus.CONFLICT);
        }

    }

}