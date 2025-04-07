package com.example.app_foodstore.Model;

public class CategoryModel {
    private int id;

    public int getId() {
        return id;
    }

    public CategoryModel() {
    }

    public CategoryModel(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String name;
    private String image;
}
