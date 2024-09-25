package com.traveler.friend.Services;

import com.traveler.friend.Entities.Badge;
import com.traveler.friend.Repositories.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;

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
}
