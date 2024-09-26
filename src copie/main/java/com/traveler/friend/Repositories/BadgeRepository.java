package com.traveler.friend.Repositories;

import com.traveler.friend.Entities.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Integer> {
    List<Badge> findByRequiredPoint(int requiredPoint);
    List<Badge> findByRequiredPointLessThanEqual(int requiredPoints);
}
