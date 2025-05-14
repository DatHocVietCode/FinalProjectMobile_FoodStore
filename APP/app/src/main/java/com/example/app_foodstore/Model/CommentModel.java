package com.example.app_foodstore.Model;

import java.io.Serializable;
import java.util.Date;

public class CommentModel implements Serializable {
    private int id;
    private String avatar_user;
    private String name;
    private String content;

    private Integer rating;
    private String created_at;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public CommentModel(int id, String avatar_user, String name, String content, Integer rating, String created_at) {
        this.id = id;
        this.avatar_user = avatar_user;
        this.name = name;
        this.content = content;
        this.rating = rating;
        this.created_at = created_at;
    }

    // For test only
    public CommentModel() {
    }

    public String getAvatar_user() {
        return avatar_user;
    }

    public void setAvatar_user(String avatar_user) {
        this.avatar_user = avatar_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
