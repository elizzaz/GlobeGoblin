package com.traveler.friend.DTO;

import com.traveler.friend.Entities.Badge;

import java.util.List;

public class UserDTO {
    private int userId;
    private String name;
    private int points;
    private List<Badge> badges;

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public List<Badge> getBadges() { return badges; }
    public void setBadges(List<Badge> badges) { this.badges = badges; }
}

