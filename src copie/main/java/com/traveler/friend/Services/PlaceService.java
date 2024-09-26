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
        String content = new String(Files.readAllBytes(Paths.get(inputFilePath)));

        JSONObject jsonObject = new JSONObject(content);

        if (!jsonObject.has("places")) {
            log.error("Le JSON ne contient pas la clé 'places'");
            throw new RuntimeException("Le JSON ne contient pas la clé 'places'");
        }
        JSONArray jsonArray = jsonObject.getJSONArray("places");

        JSONArray filteredArray = new JSONArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject place = jsonArray.getJSONObject(i);
            JSONObject filteredPlace = new JSONObject();

            filteredPlace.put("googleMapsUri", place.optString("googleMapsUri"));
            filteredPlace.put("websiteUri", place.optString("websiteUri"));

            JSONObject displayName = place.optJSONObject("displayName");
            if (displayName != null) {
                filteredPlace.put("displayNameOfContentChallenge", displayName.optString("text"));
            }
            filteredArray.put(filteredPlace);
        }

        return filteredArray;
    }

    Place getRandomPlace(JSONArray placesJson) {
        if (placesJson.length() == 0) {
            throw new RuntimeException("Aucune Place disponible.");
        }

        Random random = new Random();
        int index = random.nextInt(placesJson.length());
        JSONObject placeJson = placesJson.getJSONObject(index);

        Place place = new Place();
        place.setGoogleMapsUri(placeJson.getString("googleMapsUri"));

        place.setWebsiteUri(placeJson.getString("websiteUri"));
        place.setDisplayNameOfContentChallenge(placeJson.getString("displayNameOfContentChallenge"));

        log.info( "Place{" +
                "googleMapsUri='" + place.getGoogleMapsUri() + '\'' +
                ", websiteUri='" + place.getWebsiteUri() + '\'' +
                ", displayNameOfContentChallenge='" + place.getDisplayNameOfContentChallenge() + '\'' +
                '}');

        placeRepository.save(place);
        return place;
    }
}
