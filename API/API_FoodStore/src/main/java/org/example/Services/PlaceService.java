package org.example.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Model.PlaceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PlaceService {
    @Value("${google.api.key}") // Lấy API Key từ file cấu hình
    private String apiKey;

    private final RestTemplate restTemplate;

    public PlaceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PlaceDetails getPlaceDetails(String placeId) {
        String url = String.format(
                "https://maps.googleapis.com/maps/api/place/details/json?place_id=%s&key=%s",
                placeId, apiKey
        );
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            JsonNode resultNode = rootNode.path("result");
            String name = resultNode.path("name").asText("No name available");
            String address = resultNode.path("formatted_address").asText("No address available");

            return new PlaceDetails(name, address); // Return as PlaceDetails object

        } catch (JsonProcessingException e) {
            return new PlaceDetails("Error", "Error parsing response: " + e.getMessage());
        }
    }
}
