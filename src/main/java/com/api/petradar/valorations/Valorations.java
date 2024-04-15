package com.api.petradar.valorations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "PetValorations")
public class Valorations {

    @Id
    private String id;

    @Field("id_place")
    private String idPlace;

    @Field("id_user")
    private String idUser;

    @Field("avg_rating")
    private double averageRating;

    private boolean isReported;

    @Field("comment_message")
    private String commentMessage;

    @Field("tc")
    private Date timeCreated;
}
