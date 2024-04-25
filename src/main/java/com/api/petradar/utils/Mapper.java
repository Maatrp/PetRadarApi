package com.api.petradar.utils;

import com.api.petradar.place.Place;
import com.api.petradar.place.PlaceDto;
import com.api.petradar.place.PlaceGeolocation;

import java.util.Arrays;

public class Mapper {

    public static Place mapPlaceDtoToPlace(PlaceDto placeDTO) {
        Place place = new Place();

        place.setId(placeDTO.getId());
        place.setName(placeDTO.getName());
        place.setType(placeDTO.getType());
        place.setPlaceDescription(placeDTO.getDescription());
        place.setGeolocation(new PlaceGeolocation("Point", new double[]{placeDTO.getLatitude(), placeDTO.getLongitude()}));

        if (placeDTO.getPlaceImages() != null) {
            place.setPlaceImages(Arrays.stream(placeDTO.getPlaceImages()).toList());
        }

        if (placeDTO.getTags() != null) {
            place.setTags(Arrays.stream(placeDTO.getTags()).toList());
        }

        if (placeDTO.getRestrictions() != null) {
            place.setRestrictions(Arrays.stream(placeDTO.getRestrictions()).toList());
        }

        place.setAverageRating(placeDTO.getAverageRating());
        place.setAddress(placeDTO.getAddress());
        place.setZip(placeDTO.getZip());
        place.setTown(placeDTO.getTown());
        place.setWebsite(placeDTO.getWebsite());
        place.setPhone(placeDTO.getPhone());

        return place;
    }
}
