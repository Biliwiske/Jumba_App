package com.kiselev.jumba.middle.order;

public class Order {
    String date,product,status;
    int image;

    public Order(String date, String product, String status, int image) {
        this.date = date;
        this.product = product;
        this.status = status;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public String getProduct() {
        return product;
    }

    public String getStatus() {
        return status;
    }

    public int getImage() {
        return image;
    }

}
