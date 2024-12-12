package com.traveler.friend.Controller;

import com.traveler.friend.Services.PlaceService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping("/filterPlaces")
    public JSONArray filterPlaces(@RequestParam String inputFilePath) {
        try {
            return placeService.filterJsonToPlaces(inputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }
}
