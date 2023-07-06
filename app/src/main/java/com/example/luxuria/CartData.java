package com.example.luxuria;

public class CartData {
    private String productName;
    private String productId;
    private String productPrice;
    private String productPhoto;

    // Add constructor, getters, and setters


    public CartData(String productName, String productId, String productPrice, String productPhoto) {
        this.productName = productName;
        this.productId = productId;
        this.productPrice = productPrice;
        this.productPhoto = productPhoto;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }
}
