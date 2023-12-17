package com.example.renta.model;

public class Item {

    private String location;
    private String price;
    private String description;
    private String category;
    private String name;
    private String inclusions;
    private String image;


    public Item() {
    }

    public Item(String location, String price, String category, String name) {
        this.location = location;
        this.price = price;
        this.category = category;
        this.name = name;

    }

    public Item(String location, String price, String description, String category, String name, String inclusions, String image) {
        this.location = location;
        this.price = price;
        this.description = description;
        this.category = category;
        this.name = name;
        this.inclusions = inclusions;
        this.image = image;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String inclusions) {this.name = name;}

    public String getInclusions() {
        return inclusions;
    }

    public void setInclusions(String inclusions) {this.inclusions = inclusions;}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {this.category = category;}






}