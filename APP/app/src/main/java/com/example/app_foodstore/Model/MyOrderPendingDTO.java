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

    public MyOrderPendingDTO(Long idOrder, Double totalPrice, List<ProductInOrderDTO> products, String created, String status) {
        this.idOrder = idOrder;
        this.totalPrice = totalPrice;
        this.products = products;
        this.created = created;
        this.status = status;
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
