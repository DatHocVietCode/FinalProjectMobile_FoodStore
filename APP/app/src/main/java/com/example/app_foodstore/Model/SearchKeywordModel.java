package com.example.app_foodstore.Model;

public class SearchKeywordModel {
    public String getKeyword() {
        return keyword;
    }

    public SearchKeywordModel(String keyword) {
        this.keyword = keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    String keyword;
}
