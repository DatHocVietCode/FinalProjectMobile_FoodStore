package com.example.app_foodstore.Model;

public class ProductInOrderDTO {
    private Long idProduct;
    private String foodName;
    private String category;
    private int quantity;
    private String thumbnail;

    // getters, setters, builder


    public ProductInOrderDTO(Long idProduct, String foodName, String thumbnail, int quantity, String category) {
        this.idProduct = idProduct;
        this.foodName = foodName;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
        this.category = category;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}