package com.example.app_foodstore.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class VoucherModel implements Serializable {
    private String voucherName;
    private int discount;
    private int minAmount;

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public VoucherModel(String voucherName, int discount, int minAmount) {
        this.voucherName = voucherName;
        this.discount = discount;
        this.minAmount = minAmount;
    }

    public VoucherModel(String voucherName) {
        this.voucherName = voucherName;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }
    @NonNull
    @Override
    public String toString() {
        return voucherName; // đây là text hiển thị trên Spinner khi collapsed
    }
}
