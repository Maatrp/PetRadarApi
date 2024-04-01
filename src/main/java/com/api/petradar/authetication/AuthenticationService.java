package com.api.petradar.authetication;

import com.api.petradar.jwt.JwtService;
import com.api.petradar.permission.Permission;
import com.api.petradar.permission.PermissionRepository;
import com.api.petradar.user.User;
import com.api.petradar.user.UserRepository;
import com.api.petradar.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    public AuthenticationResponse login(AuthenticationRequest authRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUserName(), authRequest.getPassword()
        );


        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthToken);

        if (authentication.isAuthenticated()) {

            User user = userService.findUserByUserName(authRequest.getUserName());

            String jwt = generateJwt(user);

            return new AuthenticationResponse(jwt);

        } else {

            return null;
        }

    }

    public String generateJwt(User user) {
      return  jwtService.generateToken(user, generateExtraClaims(user));
    }

    private Map<String, Object> generateExtraClaims(User user) {

        return Map.of(
                "name", user.getName(),
                "role", user.getRole(),
                "permissions", user.getAuthorities()
        );

    }

}
