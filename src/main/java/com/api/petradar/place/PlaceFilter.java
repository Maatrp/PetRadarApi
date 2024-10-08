package com.api.petradar.place;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa los datos filtrados de un lugar.
 */
@Getter
@Setter
@NoArgsConstructor
public class PlaceFilter {

    double latitude;
    double longitude;
    int radius;
    double kmRadius;
}
