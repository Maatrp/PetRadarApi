package com.api.petradar.config.security;

import com.api.petradar.user.User;
import com.api.petradar.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Inyector de beans de seguridad para la configuración de autenticación.
 */
@Component
public class SecurityBeansInjector {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    /**
     * Bean que proporciona un AuthenticationManager para la autenticación.
     *
     * @return Un AuthenticationManager configurado.
     * @throws Exception Si hay un error al obtener el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Bean que proporciona un AuthenticationProvider para la autenticación.
     *
     * @return Un AuthenticationProvider configurado.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    /**
     * Bean que proporciona un UserDetailsService para cargar detalles de usuario durante la autenticación.
     *
     * @return Un UserDetailsService configurado.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return userName -> {
            User userFromDb = userRepository.findUserByUserName(userName);
            return userFromDb;
        };
    }

    /**
     * Bean que proporciona un PasswordEncoder para la codificación de contraseñas.
     *
     * @return Un PasswordEncoder configurado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}