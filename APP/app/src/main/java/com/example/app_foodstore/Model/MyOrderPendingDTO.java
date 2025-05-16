package com.example.app_foodstore.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class MyOrderPendingDTO implements Serializable {
    private Long idOrder;
    private Double totalPrice;
    private List<ProductInOrderDTO> products;
    private String created;
    private String status;
    private String paymentMethod;
    private Float deliveryFee;
    private Integer voucher;

    public Float getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Float deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public MyOrderPendingDTO(Long idOrder, Double totalPrice, List<ProductInOrderDTO> products, String created, String status, String paymentMethod, Float deliveryFee, Integer voucher) {
        this.idOrder = idOrder;
        this.totalPrice = totalPrice;
        this.products = products;
        this.created = created;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.deliveryFee = deliveryFee;
        this.voucher = voucher;
    }

    public Integer getVoucher() {
        return voucher;
    }

    public void setVoucher(Integer voucher) {
        this.voucher = voucher;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }


    public List<ProductInOrderDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInOrderDTO> products) {
        this.products = products;
    }
}
