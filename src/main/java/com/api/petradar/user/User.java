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
import java.util.stream.Collectors;

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

    @Override
    public String getUsername() {
        return userName;
    }


    @Override
    public boolean isAccountNonExpired() {
        return isEnabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsEnabled;
    }

}