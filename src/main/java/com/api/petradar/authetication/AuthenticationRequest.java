package com.api.petradar.authetication;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa una solicitud de autenticación.
 * Contiene las credenciales del usuario necesarias para la autenticación.
 */
@Getter
@Setter
public class AuthenticationRequest {

    private String userName;
    private String password;

}
