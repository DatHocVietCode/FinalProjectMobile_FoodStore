package com.example.app_foodstore.Model;

import java.io.Serializable;
import java.util.List;

public class MyOrderPendingDTO implements Serializable {
    private Long idOrder;
    private Double totalPrice;
    private List<ProductInOrderDTO> products;
    private java.lang.String created;
    private java.lang.String status;
    private java.lang.String paymentMethod;
    private Float deliveryFee;
    private Integer voucher;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Float deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public java.lang.String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(java.lang.String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public MyOrderPendingDTO(Long idOrder, Double totalPrice, List<ProductInOrderDTO> products,
                             java.lang.String created, java.lang.String status, java.lang.String paymentMethod, Float deliveryFee, Integer voucher,
                             String address) {
        this.idOrder = idOrder;
        this.totalPrice = totalPrice;
        this.products = products;
        this.created = created;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.deliveryFee = deliveryFee;
        this.voucher = voucher;
        this.address = address;

    }

    public Integer getVoucher() {
        return voucher;
    }

    public void setVoucher(Integer voucher) {
        this.voucher = voucher;
    }

    public java.lang.String getCreated() {
        return created;
    }

    public void setCreated(java.lang.String created) {
        this.created = created;
    }

    public java.lang.String getStatus() {
        return status;
    }

    public void setStatus(java.lang.String status) {
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
