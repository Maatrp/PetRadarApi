package com.api.petradar.description;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


/**
 * Clase que representa la descripción de un lugar.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "PetDescription")
public class Description {

    @Id
    private String id;

    @Field("id_place")
    private String idPlace;

    private String description;

    @Field("lang")
    private String language;
}