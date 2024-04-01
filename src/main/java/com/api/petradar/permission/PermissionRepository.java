package com.api.petradar.permission;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends MongoRepository<Permission, String> {
    @Query("{'_id' : ?0}")
    Permission findPermissionsByRole(String role);

}
