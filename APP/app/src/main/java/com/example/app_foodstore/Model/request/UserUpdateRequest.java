package com.example.app_foodstore.Model.request;

public class UserUpdateRequest {
    private String name;
    private String email;
    private String phone;
    private String avatarUrl;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String name, String email, String phone, String avatarUrl) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
    }

    // Getter và Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}