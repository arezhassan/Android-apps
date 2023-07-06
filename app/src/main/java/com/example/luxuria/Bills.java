package com.example.luxuria;

import java.math.BigDecimal;

public class Bills {
    String productName;
    String productPrice;

    public Bills(String productName, String productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        String cleanedPriceString = productPrice.replaceAll("[^\\d.]", "");
        cleanedPriceString = cleanedPriceString.replace("Rs.", "").replace(",", "");
        BigDecimal price = new BigDecimal(cleanedPriceString);
        return price;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
