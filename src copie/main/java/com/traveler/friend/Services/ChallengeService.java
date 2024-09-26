package com.traveler.friend.Services;

import com.traveler.friend.Entities.Challenge;
import com.traveler.friend.Entities.Place;
import com.traveler.friend.DTO.ChallengeDTO;
import com.traveler.friend.Entities.TypeChallenge;
import com.traveler.friend.Repositories.ChallengeRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    private static final Logger log = LoggerFactory.getLogger(ChallengeService.class); // Logger manuel

    @Autowired
    private PlaceService placeService;

    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    public Optional<Challenge> getChallengeById(Integer challengeId) {
        return challengeRepository.findById(challengeId);
    }

    public Optional<Challenge> getChallengeByName(String name) {
        return challengeRepository.findByName(name);
    }

    public Optional<Challenge> getChallengeByType(TypeChallenge type) {
        return challengeRepository.findByType(type);
    }

    public Challenge createChallenge(ChallengeDTO challengeDTO) {
        log.info("getType : {}", challengeDTO.getType());

        String inputFilePath = switch (challengeDTO.getType()) {
            case OUPS -> "/Users/elizzaz/Documents/friend/src/main/resources/data/places-badRestaurant.json";
            case EXPLORATION -> "/Users/elizzaz/Documents/friend/src/main/resources/data/places-abandonned.json";
            case BARATHON -> "/Users/elizzaz/Documents/friend/src/main/resources/data/places-bar.json";
            case FRISSONS -> "/Users/elizzaz/Documents/friend/src/main/resources/data/places-haunted.json";
            case SECRETS -> "/Users/elizzaz/Documents/friend/src/main/resources/data/places-hiddenBar.json";
            case HISTOIRE -> "/Users/elizzaz/Documents/friend/src/main/resources/data/places-historical.json";
            case MUSEE -> "/Users/elizzaz/Documents/friend/src/main/resources/data/places-museum.json";
            case GRAFFITI -> "/Users/elizzaz/Documents/friend/src/main/resources/data/places-streetArt.json";
            case CURIOSITES -> "/Users/elizzaz/Documents/friend/src/main/resources/data/places-uniqueShop.json";
            case NEWFOOD -> "/Users/elizzaz/Documents/friend/src/main/resources/data/places-veganFood.json";

            // Ajoute d'autres types de défi ici
            default -> throw new IllegalArgumentException("Type de défi inconnu : " + challengeDTO.getType());
        };

        JSONArray placesJson;
        try {
            log.info("inputFilePath : {}", inputFilePath);
            placesJson = placeService.filterJsonToPlaces(inputFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du filtrage des places : " + e.getMessage());
        }

        Place place = placeService.getRandomPlace(placesJson);
        log.info( "Place transféré{" +
                "googleMapsUri='" + place.getGoogleMapsUri() + '\'' +
                ", websiteUri='" + place.getWebsiteUri() + '\'' +
                ", displayNameOfContentChallenge='" + place.getDisplayNameOfContentChallenge() + '\'' +
                '}');

        place.setGoogleMapsUri(place.getGoogleMapsUri());
        place.setWebsiteUri(place.getWebsiteUri());
        place.setDisplayNameOfContentChallenge(place.getDisplayNameOfContentChallenge());


        Challenge challenge = new Challenge();
        challenge.setName(challengeDTO.getName());
        log.info("name  : {}", challenge.getName());

        challenge.setDescription(challengeDTO.getDescription());
        log.info("description  : {}", challenge.getDescription());

        challenge.setScore(challengeDTO.getScore());
        log.info(" score : {}", challenge.getScore());

        challenge.setType(challengeDTO.getType());
        log.info(" type : {}", challenge.getType());

        challenge.setPlace(place);
        log.info("place  : {}", challenge.getPlace());

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

}
