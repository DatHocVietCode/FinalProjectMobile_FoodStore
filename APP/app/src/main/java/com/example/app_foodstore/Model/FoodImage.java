package com.example.app_foodstore.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodImage implements Serializable {
    public FoodImage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public FoodModel getFood() {
        return food;
    }

    public void setFood(FoodModel food) {
        this.food = food;
    }

    @JsonProperty("id")
    private Long id;
    @JsonProperty("image_url")
    private String imageUrl;
    private FoodModel food;

}
