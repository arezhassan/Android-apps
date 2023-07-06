package com.example.luxuria;

public class OrderData {
    String productName;
    String price;
    String orderId;
    String orderStatus;

    public OrderData(String productName, String price, String orderId, String orderStatus) {
        this.productName = productName;
        this.price = price;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
