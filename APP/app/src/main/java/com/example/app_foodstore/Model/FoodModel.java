package com.example.app_foodstore.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

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

    private String category_name;
    @JsonProperty("price")
    private Float price;
    @JsonProperty("product_images")
    private List<FoodImage> product_images;

    @JsonProperty("average_rating")
    private Float average_rating;

    @JsonProperty("count_comment")
    private Integer count_comment;

    @JsonProperty("comments")
    private List<CommentModel> comments;

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }


    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setProduct_images(List<FoodImage> product_images) {
        this.product_images = product_images;
    }

    public Integer getCount_comment() {
        return count_comment;
    }

    public void setCount_comment(Integer count_comment) {
        this.count_comment = count_comment;
    }

    public Float getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(Float average_rating) {
        this.average_rating = average_rating;
    }

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

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public FoodModel(String category_name, String name, Long id) {
        this.category_name = category_name;
        this.name = name;
        this.id = id;
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

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public List<FoodImage> getProduct_images() {
        return product_images;
    }
}
