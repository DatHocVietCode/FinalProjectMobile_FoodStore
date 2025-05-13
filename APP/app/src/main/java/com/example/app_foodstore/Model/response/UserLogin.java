package com.example.app_foodstore.Model.response;

public class UserLogin {
    private int id;
    private String username;
    private String role;
    private String fullname;

    // Getters and setters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getFullname() { return fullname; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setRole(String role) { this.role = role; }
    public void setFullname(String fullname) { this.fullname = fullname; }
}
