package com.example.app_foodstore.APIService;

import com.example.app_foodstore.Model.Data;

public class APIResponePagination {
    private int status;
    private String message;
    private Data data;
    public APIResponePagination(int status, String message, Data data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
