package com.example.app_foodstore.Model;

import java.io.Serializable;

public class AddressModel implements Serializable {
    int idAddress;
    String address;
    String type;

    public AddressModel(int idAddress, String address, String type) {
        this.idAddress = idAddress;
        this.address = address;
        this.type = type;
    }

    public AddressModel() {
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
