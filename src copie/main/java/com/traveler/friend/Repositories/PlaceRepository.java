package com.traveler.friend.Repositories;

import com.traveler.friend.Entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
    List<Place> getPlacesByCity(String city);
    Place findById(int placeId);

}
