package com.api.petradar.loader;

import com.api.petradar.place.Place;
import com.api.petradar.place.PlaceGeolocation;
import com.api.petradar.place.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping(path = "/loader")
public class LoaderController {

    @Autowired
    private PlaceService placeService;
    @CrossOrigin
    @GetMapping("/load/")
    public ResponseEntity<Object> load() {

        String csvFile = "placescsv - PetPlace.tsv";
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] values = line.split("\t");
                    if (values[0].equals("name_place")) continue;
                    Place place = new Place();

                    place.setName(values[0]);
                    place.setStatus(values[1]);
                    place.setType(values[2]);
                    place.setAverageRating(Double.parseDouble(values[3]));
                    place.setWebsite(values[4]);
                    place.setPhone(values[5]);
                    double latitude = Double.parseDouble(values[6].replace(",", "."));
                    double longitud = Double.parseDouble(values[7].replace(",", "."));
                    place.setGeolocation(new PlaceGeolocation("Point", new double[]{latitude, longitud}));
                    String tags = values[8];
                    if(!tags.isEmpty()){
                        String[] tagsArray = tags.split(",");
                        place.setTags(Arrays.stream(tagsArray).toList());
                    }

                    addPlaceAccessibility(place, values);
                    place.setAddress(values[11]);
                    place.setTown(values[12]);
                    place.setZip(values[13]);

                    placeService.createPlace(place);
                }
                catch(Exception ex){
                    System.err.println(ex);
                }
            }
            br.close();
            System.out.println("Datos insertados en MongoDB con éxito.");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private void addPlaceAccessibility(Place place, String[] values) {
    }
}
