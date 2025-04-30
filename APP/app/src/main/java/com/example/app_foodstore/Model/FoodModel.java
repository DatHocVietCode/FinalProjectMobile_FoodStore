package com.example.app_foodstore.Model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

// Chưa có avatar nha
public class FoodModel implements Serializable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("category_id")
    private Long categoryId;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("description")
    private String description;
    @JsonProperty("sold")
    private Long sold;
    @JsonProperty("name")
    private String name;
    private String categoryName;
    @JsonProperty("price")
    private Float price;
    @JsonProperty("product_images")
    private List<FoodImage> productImages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public FoodModel(String categoryName, String name, int id) {
        this.categoryName = categoryName;
        this.name = name;
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Float getPrice() {
        return price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public Long getSold() {
        return sold;
    }

    public List<FoodImage> getProductImages() {
        return productImages;
    }
}
