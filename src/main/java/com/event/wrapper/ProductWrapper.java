package com.event.wrapper;

import lombok.Data;


@Data
public class ProductWrapper {
    Integer id;
    String name;
    String description;
    String location;
    Integer price;
    Integer categoryId;
    String categoryName;
    String imgUrl;

    public ProductWrapper() {
    }

    public ProductWrapper(Integer id, String name, String description, String location, Integer price, Integer categoryId, String categoryName, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.imgUrl = imgUrl;
    }

    public ProductWrapper(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductWrapper(Integer id, String name, String description, Integer price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }
}
