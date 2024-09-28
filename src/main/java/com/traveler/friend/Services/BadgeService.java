package com.traveler.friend.Services;

import com.traveler.friend.Entities.Badge;
import com.traveler.friend.Entities.User;
import com.traveler.friend.Repositories.BadgeRepository;
import com.traveler.friend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private UserRepository userRepository;

    public Badge createBadge(Badge badge) {
        return badgeRepository.save(badge);
    }

    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    public Optional<Badge> getBadgeById(int badgeId) {
        return badgeRepository.findById(badgeId);
    }

    public List<Badge> getBadgesByRequiredPoints(int requiredPoints) {
        return badgeRepository.findByRequiredPoint(requiredPoints);
    }

    public Badge updateBadge(int badgeId, Badge updatedBadge) {
        Optional<Badge> badgeOpt = badgeRepository.findById(badgeId);
        if (badgeOpt.isPresent()) {
            Badge badge = badgeOpt.get();
            badge.setName(updatedBadge.getName());
            badge.setDescription(updatedBadge.getDescription());
            badge.setRequiredPoint(updatedBadge.getRequiredPoint());
            return badgeRepository.save(badge);
        }
        return null;
    }

    public void deleteBadge(int badgeId) {
        badgeRepository.deleteById(badgeId);
    }

    private Badge createBadge(String name, String description, int requiredPoint) {
        Badge badge = new Badge();
        badge.setName(name);
        badge.setDescription(description);
        badge.setRequiredPoint(requiredPoint);
        return badge;
    }

    // Méthode pour obtenir le badge en fonction des points de l'utilisateur
    public List<Badge> getUserBadges(int userPoints) {
        return badgeRepository.findByRequiredPointLessThanEqual(userPoints);
    }

    public Map<String, Object> calculateProgressToNextBadge(int userId) {
        Optional<User> user = userRepository.findById(userId);

        // Assurez-vous que l'utilisateur existe
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }

        int userPoints = user.get().getPoints();

        List<Badge> badges = badgeRepository.findAll();
        badges.sort(Comparator.comparingInt(Badge::getRequiredPoint));

        Badge nextBadge = null;
        int pointsToNextBadge = 0;

        // Trouver le prochain badge à atteindre
        for (Badge badge : badges) {
            if (userPoints < badge.getRequiredPoint()) {
                nextBadge = badge;
                pointsToNextBadge = badge.getRequiredPoint() - userPoints;
                break;
            }
        }

        // Si l'utilisateur a déjà tous les badges
        if (nextBadge == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Tous les badges ont été obtenus");
            response.put("progress", 100);
            return response;
        }

        // Construire la réponse avec les informations sur le prochain badge
        Map<String, Object> response = new HashMap<>();
        response.put("nextBadge", nextBadge.getName());
        response.put("pointsToNextBadge", pointsToNextBadge);
        response.put("requiredPointsForNextBadge", nextBadge.getRequiredPoint());

        // Calcul de la progression par rapport au badge précédent
        int previousThreshold = 0;
        for (Badge badge : badges) {
            if (userPoints >= badge.getRequiredPoint()) {
                previousThreshold = badge.getRequiredPoint();
            } else {
                break;
            }
        }

        // Calcul du pourcentage de progression
        int progressPercentage = ((userPoints - previousThreshold) * 100) / (nextBadge.getRequiredPoint() - previousThreshold);
        response.put("progressPercentage", progressPercentage);

        return response;
    }


}

