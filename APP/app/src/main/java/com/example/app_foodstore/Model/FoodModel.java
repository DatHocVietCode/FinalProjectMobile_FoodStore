package com.example.app_foodstore.Model;


// Chưa có avatar nha
public class FoodModel {
    private int id;

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

    private String name;
    private String categoryName;

}
