package com.api.petradar.user;

import com.api.petradar.email.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.ws.rs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {

        try {
            boolean created = userService.createUser(user);

            if (created) {
                boolean isEmailSent = emailService.sendEmail(user.getEmail());

                if (isEmailSent) {
                    return new ResponseEntity<>("Usuario creado con exito", HttpStatus.CREATED);

                } else {
                    userService.deleteByUserName(user.getUsername());
                    return new ResponseEntity<>("No se ha podido crear el usuario.", HttpStatus.NO_CONTENT);

                }
            } else {
                return new ResponseEntity<>("El usario ya existe en la base de datos.", HttpStatus.CONFLICT);

            }

        } catch (Exception e) {
            return new ResponseEntity<>("No se ha podido crear el usuario.", HttpStatus.NO_CONTENT);

        }
    }

    @GetMapping("/validate-email")
    public ResponseEntity<String> validateUser(@PathParam("email") String email, @PathParam("token") String token) {

        boolean isVerified = userService.emailVerified(email, token);

        if (isVerified) {
            return new ResponseEntity<>("Email validado con exito", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("No se ha podido comprobar el email.", HttpStatus.CONFLICT);

        }

    }
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('MODIFY_USER')")
    @PutMapping("/modify")
    public ResponseEntity<String> modifyUser(@RequestBody User user, @RequestHeader("Authorization") String token) {

        boolean modified = userService.updateUser(user, tokenWithOutBearer(token));

        if (modified) {
            return new ResponseEntity<>("Usuario modificado con exito", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("No se ha podido modificar el usuario.", HttpStatus.CONFLICT);
        }
    }
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("/delete/{userName}")
    public ResponseEntity<String> deleteUser(@PathVariable String userName, @RequestHeader("Authorization") String token) {

        boolean deleted = userService.deleteByUserNameAndToken(userName, tokenWithOutBearer(token));

        if (deleted) {
            return new ResponseEntity<>("Usuario eliminado con exito", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("No se ha podido eliminar el usuario.", HttpStatus.CONFLICT);

        }

    }

    private String tokenWithOutBearer(String token){
        return token.replace("Bearer ", "");
    }

}