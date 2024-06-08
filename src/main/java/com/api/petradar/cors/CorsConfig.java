package com.api.petradar.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing) para permitir solicitudes desde cualquier origen.
 */
@Configuration
public class CorsConfig {

    /**
     * Bean que configura el filtro CORS para permitir solicitudes desde cualquier origen, cualquier encabezado
     * y cualquier método HTTP.
     *
     * @return El filtro CORS configurado.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir todas las solicitudes desde cualquier origen
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}