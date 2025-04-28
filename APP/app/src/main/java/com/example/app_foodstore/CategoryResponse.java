package com.example.app_foodstore;

import com.example.app_foodstore.Model.CategoryModel;
import java.util.List;

public class CategoryResponse {
    private String message;
    private String status;
    private List<CategoryModel> data;
    public List<CategoryModel> getData() { return data; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
}
