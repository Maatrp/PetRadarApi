package com.api.petradar.favorites;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;



/**
 * Clase que representa un lugar favorito.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "PetFavorites")
public class Favorite {

    @Id
    private String id;

    @Field("id_place")
    private String placeId;

    @Field("id_user")
    private String userId;

    @Field("tc")
    private Date timeCreated;

}