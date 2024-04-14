package com.api.petradar.jwt.filter;

import com.api.petradar.jwt.JwtService;
import com.api.petradar.user.User;
import com.api.petradar.user.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Obtener el header que contiente el jwt
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Obtener el jwt
            String jwt = authHeader.split(" ")[1];

            // Obener el userName del jwt
            String userName = jwtService.extractUsername(jwt);

            // Seteamos un objeto Authentication dentro del SecurityContext
            User user = userService.findUserByUserName(userName);


            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userName, null, user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);

            // Ejecutamos el filtro
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException eje) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Token expirado");

        } catch (SignatureException se) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Token incorrecto");

        }
    }
}