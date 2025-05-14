package com.example.app_foodstore.Model;

import java.io.Serializable;
import java.util.Date;

public class OrderModel implements Serializable {
    // Sau chỉnh lại cho khớp API
    private boolean isCompleted;
    private Date orderDate;
    private int orderId;
    private int paymentMethod;
    private int orderStatus;
    private float totalAmount;

    public OrderModel(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public OrderModel(boolean isCompleted, Date orderDate, int orderId, int paymentMethod, int orderStatus, float totalAmount) {
        this.isCompleted = isCompleted;
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
    }

    public OrderModel(boolean isCompleted, Date orderDate) {
        this.isCompleted = isCompleted;
        this.orderDate = orderDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMethod() {
        switch (paymentMethod){
            case 0:
                return "Cash";
            case 1:
                return "Credit Card";
            case 2:
                return "ZaloPay";
            default:
                return "";
        }
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderStatus() {
        switch (orderStatus){
            case 0:
                return "Completed";
            case 1:
                return "Ongoing";
            case 2:
                return "Canceled";
            default:
                return "";
        }
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
