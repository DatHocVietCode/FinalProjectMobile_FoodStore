package com.example.app_foodstore.Model.request;

public class CommentRequestDTO {
    private Long productId;
    private String content;
    private int star;

    public CommentRequestDTO(Long productId, String content, int star) {
        this.productId = productId;
        this.content = content;
        this.star = star;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}