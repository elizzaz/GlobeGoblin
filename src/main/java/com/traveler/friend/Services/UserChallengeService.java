package com.traveler.friend.Services;

import com.traveler.friend.Entities.Status;
import com.traveler.friend.Entities.UserChallenge;
import com.traveler.friend.Entities.User;
import com.traveler.friend.Entities.Challenge;
import com.traveler.friend.Repositories.UserChallengeRepository;
import com.traveler.friend.Repositories.UserRepository;
import com.traveler.friend.Repositories.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserChallengeService {

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    //associate un User et un Challenge donnés.
    public UserChallenge createUserChallenge(int userId, int challengeId, Status status) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Challenge> challengeOpt = challengeRepository.findById(challengeId);

        if (userOpt.isPresent() && challengeOpt.isPresent()) {
            UserChallenge userChallenge = new UserChallenge();
            userChallenge.setUser(userOpt.get());
            userChallenge.setChallenge(challengeOpt.get());
            userChallenge.setStatus(status);
            return userChallengeRepository.save(userChallenge);
        }
        return null;
    }

    public List<UserChallenge> getAllUserChallenges() {
        return userChallengeRepository.findAll();
    }

    public Optional<UserChallenge> getUserChallengeById(int id) {
        return userChallengeRepository.findById(id);
    }

    public List<UserChallenge> getUserChallengesByUserId(int userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            return userChallengeRepository.findByUser(userOpt.get());
        }
        return Collections.emptyList();
    }


    public List<UserChallenge> getUserChallengesByChallengeId(int challengeId) {
        Optional<Challenge> challengeOpt = challengeRepository.findById(challengeId);
        if (challengeOpt.isPresent()) {
            return userChallengeRepository.findByChallenge(challengeOpt.get());
        }
        return Collections.emptyList();
    }


    public List<UserChallenge> getUserChallengesByStatus(Status status) {
        return userChallengeRepository.findByStatus(status);
    }

    public UserChallenge updateUserChallengeStatus(int id, Status newStatus) {
        Optional<UserChallenge> userChallengeOpt = userChallengeRepository.findById(id);
        if (userChallengeOpt.isPresent()) {
            UserChallenge userChallenge = userChallengeOpt.get();
            userChallenge.setStatus(newStatus);
            return userChallengeRepository.save(userChallenge);
        }
        return null;
    }

    public void deleteUserChallenge(int id) {
        userChallengeRepository.deleteById(id);
    }
}
