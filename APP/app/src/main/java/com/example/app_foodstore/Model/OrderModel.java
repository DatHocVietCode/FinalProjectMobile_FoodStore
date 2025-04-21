package com.example.app_foodstore.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderModel implements Serializable {
    private boolean isCompleted;
    private Date orderDate;

    public boolean isCompleted() {
        return isCompleted;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderModel(boolean isCompleted, Date orderDate) {
        this.isCompleted = isCompleted;
        this.orderDate = orderDate;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public OrderModel(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
