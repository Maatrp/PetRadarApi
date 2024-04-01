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
public class PlaceAccessibility {

    @Field("pet_type")
    private String petType;

    @Field("max_size")
    private String maxSize;

    private boolean carrier;

    private boolean muzzle;

    private boolean free;

    private boolean terrace;
    @Field("interior_access")
    private boolean interiorAccess;
}
