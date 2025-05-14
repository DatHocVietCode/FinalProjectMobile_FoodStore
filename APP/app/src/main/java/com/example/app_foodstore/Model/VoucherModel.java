package com.example.app_foodstore.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class VoucherModel {
    private int id;
    private String code;
    private String name;
    private int minAmount;  // CamelCase cho trường min_amount
    private Integer discount;  // Sử dụng Integer để có thể chứa null

    // Getters và setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}