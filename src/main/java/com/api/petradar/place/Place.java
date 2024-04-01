package com.api.petradar.place;

import com.api.petradar.placeimages.PlaceImage;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "PetPlace")
public class Place {

    @Id
    private String id;

    @Field("name_place")
    private String name;

    private String status;

    @Field("place_type")
    private String type;

    @Field("avg_rating")
    private double averageRating;

    private String address;

    private String zip;

    private String town;

    private String website;

    private String phone;

    @Field("geo")
    @GeoSpatialIndexed(name = "geo")
    private PlaceGeolocation geolocation;

    private List<String> tags;

    @Field("accessibility")
    private PlaceAccessibility accessibility;

    @Field("tc")
    private Date timeCreated;

    @Field("tu")
    private Date  timeUpdated;

    @Field("tac")
    private Date  timeAccepted;

    @Field("tdc")
    private Date  timeDeclined;

    @Transient
    private List<PlaceImage> placeImages;

    @Transient
    private boolean isFavorite;

}