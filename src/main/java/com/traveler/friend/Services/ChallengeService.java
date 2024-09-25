package com.traveler.friend.Services;

import com.traveler.friend.Entities.Challenge;
import com.traveler.friend.Entities.Place;
import com.traveler.friend.DTO.ChallengeDTO;
import com.traveler.friend.Repositories.ChallengeRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ChallengeService {

    @Autowired
    ChallengeRepository challengeRepository;


    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    public Optional<Challenge> getChallengeById(Integer challengeId) {
        return challengeRepository.findById(challengeId);
    }

    public Optional<Challenge> getChallengeByName(String name) {
        return challengeRepository.findByName(name);
    }

    public Optional<Challenge> getChallengeByType(String type) {
        return challengeRepository.findByType(type);
    }

//    public Challenge createChallenge(Challenge challenge) {
//        return challengeRepository.save(challenge);
//    }

//    public Challenge createChallenge(String type, String challengeName, String challengeDescription) {
//        JSONArray placesJson = loadPlacesJson(type);
//        Place place = getRandomPlace(placesJson);
//
//        Challenge challenge = new Challenge();
//        challenge.setName(challengeName);
//        challenge.setDescription(challengeDescription);
//        challenge.setPlace(place);
//
//        return challengeRepository.save(challenge);
//    }

    public Challenge createChallenge(ChallengeDTO challengeDTO) {
        JSONArray placesJson = loadPlacesJson(challengeDTO.getType());
        Place place = getRandomPlace(placesJson);

        Challenge challenge = new Challenge();
        challenge.setName(challengeDTO.getName());
        challenge.setDescription(challengeDTO.getDescription());
        challenge.setPlace(place);

        return challengeRepository.save(challenge);
    }

    public void deleteChallenge(Integer challengeId) {
        challengeRepository.deleteById(challengeId);
    }

    public Challenge updateChallenge(Integer challengeId, Challenge updatedChallenge) {
        Optional<Challenge> existingChallenge = challengeRepository.findById(challengeId);
        if(existingChallenge.isPresent()) {
            Challenge challenge = existingChallenge.get();
            challenge.setName(updatedChallenge.getName());
            challenge.setDescription(updatedChallenge.getDescription());
            challenge.setScore(updatedChallenge.getScore());
            return challengeRepository.save(challenge);
        }
        return null;
    }


    private JSONArray loadPlacesJson(String type) {
        String jsonUri = switch (type) {
            case "FOOD" -> "/data/places.badRestaurant.json";
            case "decouverteCity" -> "/data/places.abandonned.json";
            // Ajoute d'autres types de défi si nécessaire
            default -> throw new IllegalArgumentException("Type de défi inconnu : " + type);
        };

        // return fetchJsonData(jsonFilePath);
        // Charger et retourner le contenu du fichier JSON
        try {
            InputStream inputStream = getClass().getResourceAsStream(jsonUri);
            if (inputStream == null) {
                throw new RuntimeException("Le fichier JSON n'a pas été trouvé : " + jsonUri);
            }
            String jsonContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return new JSONArray(jsonContent);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du JSON : " + e.getMessage());
        }
    }


    private Place getRandomPlace(JSONArray placesJson) {
        Random random = new Random();
        int index = random.nextInt(placesJson.length());
        JSONObject placeJson = placesJson.getJSONObject(index);

        Place place = new Place();
        place.setGoogleMapsUri(placeJson.getString("googleMapsUri"));
        place.setWebsiteUri(placeJson.getString("websiteUri"));
        place.setDisplayNameOfContentChallenge(placeJson.getString("displayNameOfContentChallenge"));
        // Assure-toi d'ajouter d'autres attributs de la classe Place si nécessaire

        return place;
    }


}
