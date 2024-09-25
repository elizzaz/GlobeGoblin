package com.traveler.friend.Services;

import com.traveler.friend.Entities.Place;
import com.traveler.friend.Repositories.PlaceRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

@Service
public class PlaceService {

    @Autowired
    PlaceRepository placeRepository;
    private static final Logger log = LoggerFactory.getLogger(PlaceService.class); // Logger manuel

    public JSONArray filterJsonToPlaces(String inputFilePath) throws Exception {
        log.info("Début du filtrage des places à partir du fichier : {}", inputFilePath);
        String content = new String(Files.readAllBytes(Paths.get(inputFilePath)));

        JSONObject jsonObject = new JSONObject(content);

        if (!jsonObject.has("places")) {
            log.error("Le JSON ne contient pas la clé 'places'");
            throw new RuntimeException("Le JSON ne contient pas la clé 'places'");
        }
        JSONArray jsonArray = jsonObject.getJSONArray("places");
        log.info("Nombre d'objets dans le tableau : {}", jsonArray.length());

        JSONArray filteredArray = new JSONArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject place = jsonArray.getJSONObject(i);
            JSONObject filteredPlace = new JSONObject();

            // Extrait les informations désirées
            filteredPlace.put("googleMapsUri", place.optString("googleMapsUri"));
            filteredPlace.put("websiteUri", place.optString("websiteUri"));

            JSONObject displayName = place.optJSONObject("displayName");
            if (displayName != null) {
                filteredPlace.put("displayNameOfContentChallenge", displayName.optString("text"));
            }
            log.info("filteredPlace : {}", filteredPlace);
            filteredArray.put(filteredPlace);
        }

        log.info("Filtrage terminé, {} places filtrées.", filteredArray.length());
        return filteredArray;
    }

    Place getRandomPlace(JSONArray placesJson) {
        if (placesJson.length() == 0) {
            throw new RuntimeException("Aucune Place disponible.");
        }

        Random random = new Random();
        int index = random.nextInt(placesJson.length());
        log.info("Index aléatoire sélectionné : {}", index);
        JSONObject placeJson = placesJson.getJSONObject(index);
        log.info("Place sélectionnée : {}", placeJson.toString());

        Place place = new Place();
        place.setGoogleMapsUri(placeJson.getString("googleMapsUri"));
        log.info("Maps uri : {}", place.getGoogleMapsUri());

        place.setWebsiteUri(placeJson.getString("websiteUri"));
        place.setDisplayNameOfContentChallenge(placeJson.getString("displayNameOfContentChallenge"));

        log.info("Place to string : {}", place);


        log.info( "Place{" +
                "googleMapsUri='" + place.getGoogleMapsUri() + '\'' +
                ", websiteUri='" + place.getWebsiteUri() + '\'' +
                ", displayNameOfContentChallenge='" + place.getDisplayNameOfContentChallenge() + '\'' +
                '}');

        placeRepository.save(place);
        return place;
    }
}
