package com.api.petradar.authetication;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador que maneja las solicitudes de autenticación.
 * Proporciona un endpoint para autenticar usuarios y generar tokens JWT.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    /**
     * Endpoint para autenticar un usuario.
     *
     * @param authenticationRequest La solicitud de autenticación que contiene las credenciales del usuario.
     * @return Una respuesta que contiene el token JWT si la autenticación es exitosa, o una respuesta de error si falla.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        try {
            AuthenticationResponse jwtDto = authenticationService.login(authenticationRequest);
            return new ResponseEntity<>(jwtDto,HttpStatus.OK);

        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
