package com.api.petradar.place;

import com.api.petradar.placeimages.PlaceImage;
import com.api.petradar.valorations.Valorations;
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
    @NonNull
    private String name;

    private String status;

    @Field("place_type")
    @NonNull
    private String type;

    @Field("avg_rating")
    private double averageRating;

    @Transient
    private String placeDescription;

    private String address;

    private String zip;

    private String town;

    private String website;

    private String phone;

    @Field("geo")
    @GeoSpatialIndexed(name = "geo")
    private PlaceGeolocation geolocation;

    private List<String> tags;

    private List<String> restrictions;

    @Field("user_email")
    private String email;

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

    @Transient
    private List<Valorations> valorations;


}