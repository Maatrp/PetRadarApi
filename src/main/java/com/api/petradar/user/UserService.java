package com.api.petradar.user;

import com.api.petradar.jwt.JwtService;
import com.api.petradar.permission.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;

/**
 * Servicio para manejar operaciones relacionadas con usuario.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private JwtService jwtService;

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param user El usuario a crear.
     * @return true si el usuario se creó con éxito, false si el usuario ya existe.
     */
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

    /**
     * Verifica el correo electrónico de un usuario utilizando un token.
     *
     * @param email El correo electrónico a verificar.
     * @param token El token de verificación.
     * @return true si el correo electrónico se verificó con éxito, false en caso contrario.
     */
    public boolean emailVerified(String email, String token) {
        boolean isVerified = false;

        User userByJwt = extractUserNameFromJwt(token);

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

    /**
     * Encuentra un usuario por su nombre de usuario.
     *
     * @param userName El nombre de usuario.
     * @return El usuario correspondiente al nombre de usuario especificado.
     * @throws NullPointerException si el usuario no existe.
     */
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

    /**
     * Encuentra un usuario por su correo electrónico.
     *
     * @param email El correo electrónico.
     * @return El usuario correspondiente al correo electrónico especificado.
     * @throws NullPointerException si el usuario no existe.
     */
    public User findUserByEmail(String email) {

        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new NullPointerException("El usuario no existe");
        } else {
            System.out.println(user);
            return user;
        }
    }

    /**
     * Actualiza un usuario.
     *
     * @param user  El usuario con los nuevos datos.
     * @param token El token de autenticación.
     * @return true si el usuario se actualizó con éxito, false en caso contrario.
     */
    public boolean updateUser(User user,String token) {
        User userByJwt = extractUserNameFromJwt(token);
        String userName = user.getUsername();
        boolean isUpdated = false;

        if (userName.equals(userByJwt.getUsername())) {
            userRepository.updateByUserName(userName, user);
            isUpdated = true;
        }

        return isUpdated;

    }

    /**
     * Elimina un usuario por su nombre de usuario.
     *
     * @param userName El nombre de usuario.
     * @return true si el usuario se eliminó con éxito, false en caso contrario.
     */
    public boolean deleteByUserName(String userName) {
        boolean exists = userRepository.existsByUserName(userName);
        boolean isDeleted = false;

        if (exists) {
            userRepository.deleteByUserName(userName);
            isDeleted= true;

        }

        return isDeleted;
    }

    /**
     * Elimina un usuario por su nombre de usuario y token de autenticación.
     *
     * @param userName El nombre de usuario.
     * @param token    El token de autenticación.
     * @return true si el usuario se eliminó con éxito, false en caso contrario.
     */
    public boolean deleteByUserNameAndToken(String userName, String token) {
        User userByJwt = extractUserNameFromJwt(token);

        boolean isDeleted = false;

        if (userName.equals(userByJwt.getUsername())) {
            userRepository.deleteByUserName(userName);
            isDeleted = true;
        }

        return isDeleted;
    }

    /**
     * Extrae el nombre de usuario de un token JWT.
     *
     * @param token El token JWT.
     * @return El usuario correspondiente al nombre de usuario extraído del token.
     */
    private User extractUserNameFromJwt(String token) {
        String userName = jwtService.extractUsername(token);

        User userByJwt = userRepository.findUserByUserName(userName);

        return userByJwt;
    }
}