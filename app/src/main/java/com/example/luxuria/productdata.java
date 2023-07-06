package com.example.luxuria;

import android.net.Uri;
import android.widget.LinearLayout;

public class productdata {
    String productname;
    String productprice;
    String img;
    int imgind;


        public productdata(String productname, String productprice, String img,int imgind) {
        this.productname = productname;
        this.productprice = productprice;
        this.img = img;
        this.imgind=imgind;
    }

    public String getImg() {
        return img;
    }
public void setImg(String img) {
        this.img = img;
    }


    public String getProductname() {

        return productname;
    }

    public String getProductprice() {

        return productprice;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    @Override
    public String toString() {
        return "productdata{" +
                "productname='" + productname + '\'' +
                ", productprice='" + productprice + '\'' +
                '}';
    }

    public String getProductphoto() {
        return img;
    }
}
