package com.example.app_foodstore.Model.request;

import java.io.Serializable;

public class UserLoginReq implements Serializable {


    private String username;

    private String password;

    public UserLoginReq(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
