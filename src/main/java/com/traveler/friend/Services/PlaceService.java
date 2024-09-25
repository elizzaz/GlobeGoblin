package com.traveler.friend.Services;

import com.traveler.friend.Repositories.PlaceRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PlaceService {

    @Autowired
    PlaceRepository placeRepository;

    public JSONArray filterJsonToPlaces(String inputFilePath) throws Exception {
//        log.info("Début du filtrage des places à partir du fichier : {}", inputFilePath);
        String content = new String(Files.readAllBytes(Paths.get(inputFilePath)));
        JSONArray jsonArray = new JSONArray(content);
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
}
