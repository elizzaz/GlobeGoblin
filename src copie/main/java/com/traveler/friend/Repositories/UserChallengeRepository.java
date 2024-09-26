package com.traveler.friend.Repositories;

import com.traveler.friend.Entities.Challenge;
import com.traveler.friend.Entities.User;
import com.traveler.friend.Entities.Status;
import com.traveler.friend.Entities.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, Integer> {
    List<UserChallenge> findByStatus(Status status);

    List<UserChallenge> findByUser(User user);

    List<UserChallenge> findByChallenge(Challenge challenge);
}
