package com.traveler.friend.Controller;

import com.traveler.friend.DTO.UserDTO;
import com.traveler.friend.Entities.Badge;
import com.traveler.friend.Services.BadgeService;
import com.traveler.friend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/badges")
public class BadgeController {

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Badge>> getAllBadges() {
        List<Badge> badges = badgeService.getAllBadges();
        return ResponseEntity.ok(badges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Badge> getBadgeById(@PathVariable int id) {
        return badgeService.getBadgeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/points/{requiredPoints}")
    public ResponseEntity<List<Badge>> getBadgesByRequiredPoints(@PathVariable int requiredPoints) {
        List<Badge> badges = badgeService.getBadgesByRequiredPoints(requiredPoints);
        return ResponseEntity.ok(badges);
    }


    @GetMapping("/byUser/{userId}")
    public ResponseEntity<UserDTO> getUserWithBadges(@PathVariable int userId) {
        UserDTO userDTO = userService.getUserWithBadges(userId);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<Badge> createBadge(@RequestBody Badge badge) {
        Badge createdBadge = badgeService.createBadge(badge);
        return ResponseEntity.ok(createdBadge);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Badge> updateBadge(@PathVariable int id, @RequestBody Badge updatedBadge) {
        Badge badge = badgeService.updateBadge(id, updatedBadge);
        if (badge != null) {
            return ResponseEntity.ok(badge);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBadge(@PathVariable int id) {
        badgeService.deleteBadge(id);
        return ResponseEntity.noContent().build();
    }
}

