package com.api.petradar.place;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Una clase base que representa los datos básicos de un lugar que se pintan en una card.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceBase {

    private String id;
    private String name;
    private String type;
    private double latitude;
    private double longitude;
    private boolean isFavorite;
}