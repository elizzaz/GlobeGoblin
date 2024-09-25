package com.traveler.friend.Controller;

import com.traveler.friend.Entities.UserChallenge;
import com.traveler.friend.Services.UserChallengeService;
import com.traveler.friend.Entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userChallenges")
public class UserChallengeController {

    @Autowired
    private UserChallengeService userChallengeService;

    @PostMapping("create/{userId}/{challengeId}/{status}")
    public ResponseEntity<UserChallenge> createUserChallenge(
            @PathVariable int userId,
            @PathVariable int challengeId,
            @PathVariable Status status) {
        UserChallenge userChallenge = userChallengeService.createUserChallenge(userId, challengeId, status);
        if (userChallenge != null) {
            return ResponseEntity.ok(userChallenge);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<UserChallenge>> getAllUserChallenges() {
        List<UserChallenge> userChallenges = userChallengeService.getAllUserChallenges();
        return ResponseEntity.ok(userChallenges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserChallenge> getUserChallengeById(@PathVariable int id) {
        return userChallengeService.getUserChallengeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserChallenge>> getUserChallengesByUserId(@PathVariable int userId) {
        List<UserChallenge> userChallenges = userChallengeService.getUserChallengesByUserId(userId);
        return ResponseEntity.ok(userChallenges);
    }

    @GetMapping("/challenge/{challengeId}")
    public ResponseEntity<List<UserChallenge>> getUserChallengesByChallengeId(@PathVariable int challengeId) {
        List<UserChallenge> userChallenges = userChallengeService.getUserChallengesByChallengeId(challengeId);
        return ResponseEntity.ok(userChallenges);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<UserChallenge>> getUserChallengesByStatus(@PathVariable Status status) {
        List<UserChallenge> userChallenges = userChallengeService.getUserChallengesByStatus(status);
        return ResponseEntity.ok(userChallenges);
    }

    // Mettre à jour le statut d'un UserChallenge
    @PutMapping("/{id}/{status}")
    public ResponseEntity<UserChallenge> updateUserChallengeStatus(@PathVariable int id, @PathVariable Status status) {
        UserChallenge updatedChallenge = userChallengeService.updateUserChallengeStatus(id, status);
        if (updatedChallenge != null) {
            return ResponseEntity.ok(updatedChallenge);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserChallenge(@PathVariable int id) {
        userChallengeService.deleteUserChallenge(id);
        return ResponseEntity.noContent().build();
    }
}
