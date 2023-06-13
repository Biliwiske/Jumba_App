package com.kiselev.jumba.middle.store;

public class Store {
    String name,description;
    int cost;
    int image;

    public Store(String name, String description, int cost, int image) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public int getImage() {
        return image;
    }
}
