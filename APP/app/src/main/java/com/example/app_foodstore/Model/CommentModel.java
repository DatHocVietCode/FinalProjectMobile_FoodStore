package com.example.app_foodstore.Model;

import java.io.Serializable;
import java.util.Date;

public class CommentModel implements Serializable {
    private int idComment;
    //private String avatar;
    private int avatar;
    private String name;
    private String content;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public CommentModel(int idComment, int avatar, String name, String content, double rating, Date dateCreated) {
        this.idComment = idComment;
        this.avatar = avatar;
        this.name = name;
        this.content = content;
        this.rating = rating;
        this.dateCreated = dateCreated;
    }

    private double rating;
    // For test only
    public CommentModel() {
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    private Date dateCreated;

}
