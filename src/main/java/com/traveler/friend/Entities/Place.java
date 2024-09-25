package com.traveler.friend.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int placeId;
    private String city = "SYDNEY";
    private String googleMapsUri;
    private String websiteUri;
    private String displayNameOfContentChallenge;

    public int getPlaceId() {
        return placeId;
    }

    public String getGoogleMapsUri() {
        return googleMapsUri;
    }

    public String getWebsiteUri() {
        return websiteUri;
    }

    public String getDisplayNameOfContentChallenge() {
        return displayNameOfContentChallenge;
    }

    public String getCity() {
        return city;
    }

    public void setGoogleMapsUri(String googleMapsUri) {
        this.googleMapsUri = googleMapsUri;
    }

    public void setWebsiteUri(String websiteUri) {
        this.websiteUri = websiteUri;
    }

    public void setDisplayNameOfContentChallenge(String displayNameOfContentChallenge) {
        this.displayNameOfContentChallenge = displayNameOfContentChallenge;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

