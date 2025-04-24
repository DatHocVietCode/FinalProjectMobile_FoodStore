package com.example.app_foodstore.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class VoucherModel implements Serializable {
    private String voucherName;

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
