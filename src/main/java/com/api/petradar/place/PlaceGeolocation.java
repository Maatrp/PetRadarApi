package com.api.petradar.place;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceGeolocation {
    @Field("type")
    private String type;
    @Field("coord")
    private double[] coordinates;

}