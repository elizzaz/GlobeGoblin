package com.traveler.friend.Controller;

import com.traveler.friend.DTO.ChallengeDTO;
import com.traveler.friend.Entities.Challenge;
import com.traveler.friend.Entities.Status;
import com.traveler.friend.Entities.TypeChallenge;
import com.traveler.friend.Services.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @GetMapping
    public List<Challenge> getAllChallenges() {
        return challengeService.getAllChallenges();
    }

    @GetMapping("/{id}")
    public Optional<Challenge> getChallengeById(@PathVariable Integer id) {
        return challengeService.getChallengeById(id);
    }

    @GetMapping("/name/{name}")
    public Optional<Challenge> getChallengeByName(@PathVariable String name) {
        return challengeService.getChallengeByName(name);
    }

    @GetMapping("/type/{type}")
    public Optional<Challenge> getChallengeByType(@PathVariable TypeChallenge type) {
        return challengeService.getChallengeByType(type);
    }

    @PostMapping
    public ResponseEntity<Challenge> createChallenge(@RequestBody ChallengeDTO challengeDTO) {
        Challenge createdChallenge = challengeService.createChallenge(challengeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChallenge);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Challenge> updateChallenge(@PathVariable("id") Integer challengeId, @RequestBody Challenge updatedChallenge) {
        Challenge challenge = challengeService.updateChallenge(challengeId, updatedChallenge);
        return challenge != null ? ResponseEntity.ok(challenge) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable("id") Integer challengeId) {
        challengeService.deleteChallenge(challengeId);
        return ResponseEntity.noContent().build();
    }
}
