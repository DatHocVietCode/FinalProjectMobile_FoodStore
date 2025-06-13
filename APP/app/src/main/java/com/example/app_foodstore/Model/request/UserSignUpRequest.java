package com.example.app_foodstore.Model.request;

import java.io.Serializable;

public class UserSignUpRequest implements Serializable {
    private String email;
    private String userName;
    private String password;

    public UserSignUpRequest(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }
}
