package com.example.app_foodstore.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

// Chưa có avatar nha
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodModel implements Serializable {
    public FoodModel() {
    }
    @JsonProperty("id")
    private Long id;
    @JsonProperty("category_id")
    private Long category_id;
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
    private List<FoodImage> product_images;

    public Long getId() {
        return id;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public FoodModel(String categoryName, String name, Long id) {
        this.categoryName = categoryName;
        this.name = name;
        this.id = id;
    }

    public Long getCategory_id() {
        return category_id;
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

    public List<FoodImage> getProduct_images() {
        return product_images;
    }
}
