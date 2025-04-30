package com.example.app_foodstore.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class FoodImage implements Serializable {
    private Long id;
    @JsonProperty("image_url")
    private String imageUrl;
    private FoodModel food;

}
