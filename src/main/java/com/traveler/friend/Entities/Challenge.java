package com.traveler.friend.Entities;

import jakarta.persistence.*;

@Entity
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int challengeId;
    private String name;
    private String description;
    private int score;
    private String type; // "FOOD" / "STREET"

    @ManyToOne
    @JoinColumn(name = "placeId")
    private Place place;

    public int getChallengeId() {
        return challengeId;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Place getPlace() {
        return place;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPlace(Place place) {
        new Place();
    }

    public void setType(String type) {
        this.type = type;
    }
}
