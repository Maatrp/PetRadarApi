package com.api.petradar.user;

import com.api.petradar.jwt.JwtService;
import com.api.petradar.permission.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private JwtService jwtService;

    public boolean createUser(User user) {
        boolean exists = userRepository.existsByUserName(user.getUsername());
        boolean created = false;

        if (!exists) {
            user.setTimeCreated(new Date(System.currentTimeMillis()));
            user.setRole("user");
            user.setEnabled(false);
            user.setCredentialsEnabled(false);
            user.setEmailVerified(false);
            userRepository.save(user);
            System.out.println("Nuevo usuario " + user.getUsername() + " creado");
            created = true;
        } else {
            System.out.println("El usuario " + user.getUsername() + " ya existe");
        }

        return created;
    }

    public boolean emailVerified(String email, String token) {
        boolean isVerified = false;

        // Obener el userName del jwt
        String userName = jwtService.extractUsername(token);
        User userByJwt = userRepository.findUserByUserName(userName);

        if (email.equals(userByJwt.getEmail())) {
            userByJwt.setEnabled(true);
            userByJwt.setCredentialsEnabled(true);
            userByJwt.setEmailVerified(true);
            userRepository.save(userByJwt);
            isVerified = true;

        } else {
            System.out.println("Email ha podido verificarse");
        }

        return isVerified;
    }

    public User findUserByUserNamePassword(String userName, String password) {

        User user = userRepository.findUserByUserNamePassword(userName, password);
        user.setPermission(permissionRepository.findPermissionsByRole(user.getRole()));
        if (user == null) {
            throw new NullPointerException("El usuario no existe");
        } else {
            System.out.println(user);
            return user;
        }
    }

    public User findUserByUserName(String userName) {

        User user = userRepository.findUserByUserName(userName);
        user.setPermission(permissionRepository.findPermissionsByRole(user.getRole()));
        if (user == null) {
            throw new NullPointerException("El usuario no existe");
        } else {
            System.out.println(user);
            return user;
        }
    }

    public User findUserByEmail(String email) {

        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new NullPointerException("El usuario no existe");
        } else {
            System.out.println(user);
            return user;
        }
    }

    public boolean deleteUserName(String userName) {
        boolean exists = userRepository.existsByUserName(userName);
        if (exists) {
            userRepository.deleteByUserName(userName);
            return true;
        }
        return false;
    }

}