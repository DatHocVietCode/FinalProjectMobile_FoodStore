package com.example.app_foodstore.Model.request;

public class AddressRequest {
    private String address;
    private String addressType;

    private Boolean isDefault;

    public AddressRequest(String address, String addressType, Boolean isDefault) {
        this.address = address;
        this.addressType = addressType;
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

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}