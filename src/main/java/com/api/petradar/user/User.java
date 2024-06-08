package com.api.petradar.user;

import com.api.petradar.permission.Permission;
import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa un usuario en el sistema.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "PetUsers")
public class User implements UserDetails {

    @Id
    private String id;

    @Field("user_name")
    @Indexed(unique = true)
    private String userName;

    @NonNull
    private String name;

    @Indexed(unique = true)
    private String email;

    @Getter
    @Setter
    private String password;

    private String role;

    @Transient
    private Permission permission;

    private boolean isEmailVerified;

    private boolean isEnabled;

    private boolean isCredentialsEnabled;

    @Field("tc")
    private Date timeCreated;


    /**
     * Obtiene una colección de autoridades (permisos) asignadas al usuario.
     *
     * @return Una colección de autoridades.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(permission != null){
            // Mapear los permisos como autoridades
            for (String permission : permission.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }

            // Hemos de tratar al rol como un authority, de modo que tendremos un rol en la lista de permisos
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return authorities;
    }

    /**
     * Obtiene el nombre de usuario del usuario.
     *
     * @return El nombre de usuario.
     */
    @Override
    public String getUsername() {
        return userName;
    }


    /**
     * Indica si la cuenta del usuario ha expirado.
     *
     * @return true si la cuenta no ha expirado, false en caso contrario.
     */
    @Override
    public boolean isAccountNonExpired() {
        return isEnabled;
    }

    /**
     * Indica si la cuenta del usuario está bloqueada.
     *
     * @return true si la cuenta no está bloqueada, false en caso contrario.
     */
    @Override
    public boolean isAccountNonLocked() {
        return isEnabled;
    }

    /**
     * Indica si las credenciales del usuario (contraseña) han expirado.
     *
     * @return true si las credenciales no han expirado, false en caso contrario.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsEnabled;
    }

}