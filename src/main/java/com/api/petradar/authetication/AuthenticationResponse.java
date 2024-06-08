package com.api.petradar.authetication;

import com.api.petradar.user.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa la respuesta de autenticación.
 * Contiene el token JWT y la información del usuario autenticado.
 */
@Getter
@Setter
public class AuthenticationResponse {

    private String jwt;

    private User user;


    /**
     * Constructor para crear una instancia de AuthenticationResponse.
     *
     * @param jwt El token JWT generado.
     * @param user La información del usuario autenticado.
     */
    public AuthenticationResponse(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }
}
