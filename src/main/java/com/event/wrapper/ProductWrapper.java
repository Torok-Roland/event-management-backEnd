package com.event.wrapper;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductWrapper {
    Integer id;
    String name;
    LocalDateTime startDate;
    LocalDateTime endDate;
    String description;
    String location;
    String imgUrl;
    Integer categoryId;
    String categoryName;

    public ProductWrapper() {
    }

    public ProductWrapper(Integer id, String name, LocalDateTime startDate, LocalDateTime endDate, String description, String location, String imgUrl, Integer categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.location = location;
        this.imgUrl = imgUrl;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public ProductWrapper(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductWrapper(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
