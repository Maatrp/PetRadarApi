package com.api.petradar.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para realizar operaciones CRUD en la colección de usuario.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Comprueba si existe un usuario con el nombre de usuario especificado.
     *
     * @param userName El nombre de usuario.
     * @return {@code true} si existe un usuario con el nombre de usuario especificado, {@code false} en caso contrario.
     */
    boolean existsByUserName(String userName);

    /**
     * Encuentra un usuario por su nombre de usuario.
     *
     * @param userName El nombre de usuario.
     * @return El usuario correspondiente al nombre de usuario especificado.
     */
    @Query("{'userName' : ?0}")
    User findUserByUserName(String userName);

    /**
     * Encuentra un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El usuario correspondiente al ID especificado.
     */
    @Query("{'_id' : ?0}")
    User findUserById(String id);

    /**
     * Encuentra un usuario por su correo electrónico.
     *
     * @param email El correo electrónico del usuario.
     * @return El usuario correspondiente al correo electrónico especificado.
     */
    @Query("{'email' : ?0}")
    User findUserByEmail(String email);

    /**
     * Encuentra un usuario por su nombre de usuario y contraseña.
     *
     * @param userName El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return El usuario correspondiente al nombre de usuario y contraseña especificados.
     */
    @Query("{ 'userName' : ?0, 'password' : ?1}")
    User findUserByUserNamePassword(String userName, String password);

    /**
     * Actualiza un usuario por su nombre de usuario.
     *
     * @param userName El nombre de usuario.
     * @param user     El objeto usuario con los nuevos datos.
     */
    default void updateByUserName(String userName, User user) {
        User existingUser = findUserByUserName(userName);
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            save(existingUser);
        }
    }

    /**
     * Elimina un usuario por su nombre de usuario.
     *
     * @param userName El nombre de usuario.
     */
    void deleteByUserName(String userName);

}