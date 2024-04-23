package com.api.petradar.place;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceBase {
    //Pintar datos en el mapa
    private String id;
    private String name;
    private String type;
    private double latitude;
    private double longitude;
    private boolean isFavorite;
}