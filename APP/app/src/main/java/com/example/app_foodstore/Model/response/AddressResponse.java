package com.example.app_foodstore.Model.response;

import java.io.Serializable;

public class AddressResponse implements Serializable {
    private Long id;
    private String address;
    private String addressType;

    private Boolean isDefault;

    public AddressResponse(Long id, String addressType, String address, Boolean isDefault) {
        this.id = id;
        this.addressType = addressType;
        this.address = address;
        this.isDefault = isDefault;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}