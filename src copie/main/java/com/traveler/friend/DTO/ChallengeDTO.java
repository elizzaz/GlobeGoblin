package com.traveler.friend.DTO;

import com.traveler.friend.Entities.TypeChallenge;

public class ChallengeDTO {
    private TypeChallenge type;
    private String name;
    private String description;
    private int score;

    public TypeChallenge getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getScore() {
        return score;
    }

    public void setType(TypeChallenge type) {
        this.type = type;
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
}
