package com.api.petradar.place;


import com.api.petradar.favorites.Favorite;
import com.api.petradar.favorites.FavoritesRepository;
import com.api.petradar.placeimages.PlaceImage;
import com.api.petradar.placeimages.PlaceImages;
import com.api.petradar.placeimages.PlaceImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PlaceImagesRepository placeImagesRepository;
    @Autowired
    private FavoritesRepository favoritesRepository;

    public List<PlaceBase> getAllPlaces(PlaceFilter placeFilter, String userId) {
        // USAR placeFilter
        List<PlaceBase> placeBases = new ArrayList<>();

        try {
            List<Place> places = placeRepository.findAll();
            if (!places.isEmpty()) {
                for (Place place : places) {
                    PlaceBase placeBase = getPlaceBase(place);
                    placeBase.setFavorite(checkIsFavorite(placeBase.getId(), userId));
                    placeBases.add(placeBase);
                }
            }

        } catch (Exception e) {
            System.out.println("getAllPlaces: " + e);

        }

        return placeBases;
    }


    public Place getPlaceById(String id, String userId) {
        Place place = null;
        try {
            Optional<Place> optionalPlace = placeRepository.findById(id);
            if (optionalPlace.isPresent()) {
                place = optionalPlace.get();
                place.setFavorite(checkIsFavorite(place.getId(), userId));
                place.setPlaceImages(getPlaceImagesById(id));
                System.out.println(optionalPlace);
            }

        }catch (Exception e) {
            System.out.println("getPlaceById: " + e);

        }

        return place;
    }


    public List<PlaceImage> getPlaceImagesById(String id) {
        List<PlaceImage> placeImageList = null;

        try {
            Optional<PlaceImages> optionalPlaceImage = placeImagesRepository.findById(id);

            if (optionalPlaceImage.isPresent()) {
                placeImageList = optionalPlaceImage.get().getImages();
                System.out.println(optionalPlaceImage);
            }

        }catch (Exception e) {
            System.out.println("getPlaceById: " + e);

        }

        return placeImageList;
    }


    public void createPlace(Place place) {
        // Verifica si ya existe un lugar con el mismo place_name y zip
        boolean exists = placeRepository.existsByPlaceNameAndZip(place.getName(), place.getZip());

        if (!exists) {
            placeRepository.save(place);
            System.out.println("Nuevo lugar guardado: " + place);
        } else {
            System.out.println("Ya existe un lugar con el mismo place_name y zip: " + place);
        }
    }


    public List<PlaceBase> findNearPlaces(double latitude, double longitude, double maxDistanceInMeters, String userId) {

        // USAR placeFilter
        List<PlaceBase> placeBases = new ArrayList<>();

        try {
            List<Place> places = placeRepository.findNearPlaces(latitude, longitude, ((maxDistanceInMeters / 1000) / 6371));
            if (!places.isEmpty()) {
                for (Place place : places) {
                    PlaceBase placeBase = getPlaceBase(place);
                    placeBase.setFavorite(checkIsFavorite(placeBase.getId(), userId));
                    placeBases.add(placeBase);
                }
            }

        } catch (Exception e) {
            System.out.println("getAllPlaces: " + e);

        }

        return placeBases;
    }


    private static PlaceBase getPlaceBase(Place place) {
        PlaceBase placeBase = new PlaceBase();
        placeBase.setId(place.getId());
        placeBase.setName(place.getName());
        placeBase.setType(place.getType());
        if(place.getGeolocation() != null &&
                place.getGeolocation().getCoordinates() != null &&
                place.getGeolocation().getCoordinates().length == 2){
            placeBase.setLatitude(place.getGeolocation().getCoordinates()[0]);
            placeBase.setLongitude(place.getGeolocation().getCoordinates()[1]);
        }
        return placeBase;
    }

    private boolean checkIsFavorite( String placeId, String userId) {
        List<Favorite> favoriteList = favoritesRepository.findAllByUserId(userId);

        boolean isFavorite = false;
        for (Favorite favorite : favoriteList) {
            if (favorite.getPlaceId().equals(placeId)) {
                isFavorite = true;
                break;
            }
        }

        return isFavorite;
    }


}