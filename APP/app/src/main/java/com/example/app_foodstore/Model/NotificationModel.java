package com.example.app_foodstore.Model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NotificationModel implements Serializable {
    private String orderId;
    private int status;
    private String decs;
    private Date createdDate;
    private int image;
    public NotificationModel(String orderId, int status, String decs, Date createdDate, int image) {
        this.orderId = orderId;
        this.status = status;
        this.decs = decs;
        this.createdDate = createdDate;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public int getStatusId()
    {
        return status;
    }
    public String getStatus() {
        switch (status){
            case 0:
                return  "Ongoing";
            case 1:
                return "Completed";
            case 2:
                return "Canceled";
        }
        return null;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDecs() {
        return decs;
    }

    public void setDecs(String decs) {
        this.decs = decs;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public String getGapTime() {
        if (createdDate == null) {
            return "No date available";
        }

        long now = System.currentTimeMillis();
        long gap = now - createdDate.getTime();

        long minutes = TimeUnit.MILLISECONDS.toMinutes(gap);
        long hours = TimeUnit.MILLISECONDS.toHours(gap);
        long days = TimeUnit.MILLISECONDS.toDays(gap);

        if (minutes < 1) {
            return "Just now";
        } else if (minutes < 60) {
            return minutes + " mins ago";
        } else if (hours < 24) {
            return hours + " hours ago";
        } else if (days < 7) {
            return days + " days ago";
        } else {
            long weeks = days / 7;
            return weeks + " weeks ago";
        }
    }
}
