package com.traveler.friend.Services;

import com.traveler.friend.DTO.UserDTO;
import com.traveler.friend.Entities.Badge;
import com.traveler.friend.Entities.User;
import com.traveler.friend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BadgeService badgeService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new RuntimeException("Invalid credentials");
    }

    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }

    public User updateUser(Integer userId, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setEmail(updatedUser.getEmail());
            user.setName(updatedUser.getName());
            user.setPoints(updatedUser.getPoints());
            return userRepository.save(user);
        }
        return null;
    }

    public UserDTO getUserWithBadges(int userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            List<Badge> earnedBadges = badgeService.getUserBadges(user.get().getPoints());

            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.get().getUserId());
            userDTO.setName(user.get().getName());
            userDTO.setPoints(user.get().getPoints());
            userDTO.setBadges(earnedBadges);

            return userDTO;
        } else {
            throw new RuntimeException("Utilisateur non trouvé");
        }
    }
}
