package com.api.petradar.valuation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Clase que representa las valoraciones de un lugar.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "PetValuation")
public class Valuation {

    @Id
    private String id;

    @Field("id_place")
    private String placeId;

    @Field("id_user")
    private String userId;

    @Field("avg_rating")
    private double averageRating;

    private boolean isReported;

    @Field("comment_message")
    private String commentMessage;

    @Field("tc")
    private Date timeCreated;

    @Transient
    private String userName;
}
