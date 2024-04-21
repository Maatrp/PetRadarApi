package com.api.petradar.place;

import com.api.petradar.placeimages.PlaceImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto {
    private String id;
    private String name;
    private String type;
    private String description;
    private double latitude;
    private double longitude;
    private PlaceImage[] placeImages;
    private String[] tags;
    private String[] restrictions;
    private double averageRating;
    private String address;
    private String zip;
    private String town;
    private String website;
    private String phone;
    private boolean favorite;

}