package org.example.Controller;

import org.example.Model.PlaceDetails;
import org.example.Services.PlaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/place/{placeId}")
    public PlaceDetails getPlaceDetails(@PathVariable("placeId") String placeId) {
        return placeService.getPlaceDetails(placeId);
    }
}
