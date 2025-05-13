package com.example.app_foodstore.Model;

import java.io.Serializable;

public class CartModel implements Serializable {
    private int id;
    private int quantity = 1;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CartModel(int id) {
        this.id = id;
    }
}
