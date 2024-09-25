package com.traveler.friend.Repositories;

import com.traveler.friend.Entities.Challenge;
import com.traveler.friend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {
    Optional<Challenge> findByName(String name);
    Optional<Challenge> findByType(String type);
}
