package com.api.petradar.place;


import com.api.petradar.description.Description;
import com.api.petradar.description.DescriptionRepository;
import com.api.petradar.favorites.Favorite;
import com.api.petradar.favorites.FavoritesRepository;
import com.api.petradar.placeimages.PlaceImage;
import com.api.petradar.placeimages.PlaceImages;
import com.api.petradar.placeimages.PlaceImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.api.petradar.utils.mapper.mapPlaceDtoToPlace;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceImagesRepository placeImagesRepository;

    @Autowired
    private DescriptionRepository descriptionRepository;

    @Autowired
    private FavoritesRepository favoritesRepository;

    public List<PlaceBase> getAllPlaces(PlaceFilter placeFilter, String userId) {
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

        } catch (Exception e) {
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

        } catch (Exception e) {
            System.out.println("getPlaceById: " + e);

        }

        return placeImageList;
    }

    public boolean createPlace(String idUser, PlaceDto placeDto) {
        Place place = mapPlaceDtoToPlace(placeDto);

        boolean exists = placeRepository.existsByPlaceNameAndZip(place.getName(), place.getZip());

        boolean isCreated = false;

        if (!exists) {

            place.setIdUser(idUser);

            place.setStatus("PEND");

            place.setGeolocation(place.getGeolocation());

            if (place.getWebsite() == null) {
                place.setWebsite("");
            }

            if (place.getPhone() == null) {
                place.setPhone("");
            }

            place.setTimeCreated(new Date(System.currentTimeMillis()));
            place.setTimeUpdated(new Date(System.currentTimeMillis()));

            placeRepository.save(place);


            Description description = new Description();
            description.setIdPlace(place.getId());
            description.setDescription(place.getPlaceDescription());
            description.setLanguage("es");
            descriptionRepository.save(description);

            isCreated = true;
        } else {
            System.out.println("Ya existe un lugar con el mismo place_name y zip: " + place);
        }

        return isCreated;
    }

    public boolean updateStatusPlace(String placeId, String status) {
        boolean isStatusUpdated = false;

        Place place = updateStatus(placeId, status);

        if (place != null) {
            placeRepository.save(place);
            isStatusUpdated = true;
        }

        return isStatusUpdated;
    }

    @Transactional
    public boolean updateAllStatusPlaces(List<String> placeIdList, String status) {
        try {
            List<Place> placesList = new ArrayList<>();

            for (String placeId : placeIdList) {
                Place place = updateStatus(placeId, status);
                placesList.add(place);
            }

            placeRepository.saveAll(placesList);

            return true;

        } catch (Exception ex) {
            return false;

        }
    }

    public List<PlaceBase> findNearPlaces(double latitude, double longitude, double maxDistanceInMeters, String
            userId) {

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

    public void loadPlace(Place place) {
        boolean exists = placeRepository.existsByPlaceNameAndZip(place.getName(), place.getZip());

        if (!exists) {
            placeRepository.save(place);
            System.out.println("Nuevo lugar guardado: " + place);
        } else {
            System.out.println("Ya existe un lugar con el mismo place_name y zip: " + place);
        }
    }

    private static PlaceBase getPlaceBase(Place place) {
        PlaceBase placeBase = new PlaceBase();
        placeBase.setId(place.getId());
        placeBase.setName(place.getName());
        placeBase.setType(place.getType());
        if (place.getGeolocation() != null &&
                place.getGeolocation().getCoordinates() != null &&
                place.getGeolocation().getCoordinates().length == 2) {
            placeBase.setLatitude(place.getGeolocation().getCoordinates()[0]);
            placeBase.setLongitude(place.getGeolocation().getCoordinates()[1]);
        }
        return placeBase;
    }

    private boolean checkIsFavorite(String placeId, String userId) {
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

    private Place updateStatus(String placeId, String status) {
        Place place = placeRepository.findByCustomId(placeId);

        if (place != null) {
            place.setStatus(status);

            if (status.equals("AC")) {
                place.setTimeAccepted(new Date(System.currentTimeMillis()));
            } else if (status.equals("DC")) {
                place.setTimeDeclined(new Date(System.currentTimeMillis()));
            }

            place.setTimeUpdated(new Date(System.currentTimeMillis()));

        }

        return place;
    }
}