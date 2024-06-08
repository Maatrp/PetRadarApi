package com.api.petradar.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Una entidad que representa los permisos de un usuario en la aplicación.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "PetPermissions")
public class Permission {

    @Id
    private String id;

    private String[] permissions;
}