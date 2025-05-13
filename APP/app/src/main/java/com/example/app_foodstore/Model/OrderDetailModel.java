package com.example.app_foodstore.Model;

import java.io.Serializable;

public class OrderDetailModel implements Serializable {
    // Sau chỉnh lại cho khớp API
    private int foodId;
    private String foodName;
    private int price;
    private int number;
    public OrderDetailModel(int foodId, String foodName, int number) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.number = number;
    }

    public OrderDetailModel(int foodId, String foodName, int price, int number) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
        this.number = number;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }



}
