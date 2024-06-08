package com.api.petradar.permission;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de repositorio para la entidad Permission.
 * Proporciona métodos para acceder y manipular datos relacionados con los permisos en la base de datos MongoDB.
 */
@Repository
public interface PermissionRepository extends MongoRepository<Permission, String> {

    /**
     * Encuentra los permisos asociados a un determinado rol.
     *
     * @param role El rol para el cual se buscan los permisos.
     * @return El objeto Permission que contiene los permisos asociados al rol especificado.
     */
    @Query("{'_id' : ?0}")
    Permission findPermissionsByRole(String role);

}
