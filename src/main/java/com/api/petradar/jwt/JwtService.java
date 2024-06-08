package com.api.petradar.jwt;

import com.api.petradar.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Servicio para la generación y manipulación de tokens JWT (Json Web Token).
 */
@Service
public class JwtService {

    @Value("${security.jwt.expiration-minutes}")
    private long EXPIRATION_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    /**
     * Genera un token JWT para el usuario con las reclamaciones especificadas.
     *
     * @param user        El usuario para el cual se genera el token.
     * @param extraClaims Las reclamaciones adicionales para incluir en el token.
     * @return El token JWT generado.
     */
    public String generateToken(User user, Map<String, Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el nombre de usuario del token JWT especificado.
     *
     * @param jwt El token JWT del cual se extrae el nombre de usuario.
     * @return El nombre de usuario extraído del token.
     */
    public String extractUsername(String jwt) {

        return extractAllClaims(jwt)
                .getSubject();

    }

    /**
     * Genera la clave secreta para firmar y verificar el token JWT.
     *
     * @return La clave secreta generada.
     */
    private Key generateKey() {
        byte[] secretAsBytes = Decoders.BASE64.decode(SECRET_KEY);
        System.out.println("Mi token: " + Arrays.toString(secretAsBytes));
        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    /**
     * Extrae todas las reclamaciones del token JWT especificado.
     *
     * @param jwt El token JWT del cual se extraen las reclamaciones.
     * @return Las reclamaciones extraídas del token.
     */
    private Claims extractAllClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
