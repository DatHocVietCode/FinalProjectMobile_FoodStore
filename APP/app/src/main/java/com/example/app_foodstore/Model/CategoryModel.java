package com.example.app_foodstore.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryModel implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("image")
    private String image;
    public Long getId() {
        return id;
    }

    public CategoryModel() {
    }

    public CategoryModel(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
