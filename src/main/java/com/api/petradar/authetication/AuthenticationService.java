package com.api.petradar.authetication;

import com.api.petradar.jwt.JwtService;
import com.api.petradar.user.User;
import com.api.petradar.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Servicio que maneja la lógica de autenticación de usuarios.
 */
@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;


    /**
     * Autentica al usuario utilizando sus credenciales y genera un token JWT si la autenticación es exitosa.
     *
     * @param authRequest La solicitud de autenticación que contiene las credenciales del usuario.
     * @return Una respuesta de autenticación que contiene el token JWT y la información del usuario,
     *         o {@code null} si la autenticación falla.
     */
    public AuthenticationResponse login(AuthenticationRequest authRequest) {

        UsernamePasswordAuthenticationToken userNamePasswordAuthToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUserName(), authRequest.getPassword()
        );


        Authentication authentication = authenticationManager.authenticate(userNamePasswordAuthToken);

        if (authentication.isAuthenticated()) {

            User user = userService.findUserByUserName(authRequest.getUserName());

            String jwt = generateJwt(user);

            return new AuthenticationResponse(jwt, user);

        } else {

            return null;
        }

    }

    /**
     * Genera un token JWT para un usuario dado.
     *
     * @param user El usuario para el cual se genera el token JWT.
     * @return El token JWT generado.
     */
    public String generateJwt(User user) {
      return  jwtService.generateToken(user, generateExtraClaims(user));
    }

    /**
     * Genera las reclamaciones adicionales que se incluirán en el token JWT.
     *
     * @param user El usuario para el cual se generan las reclamaciones.
     * @return Un mapa de reclamaciones adicionales.
     */
    private Map<String, Object> generateExtraClaims(User user) {

        return Map.of(
                "name", user.getName(),
                "role", user.getRole(),
                "permissions", user.getAuthorities()
        );

    }

}
