package com.event.model;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Product.getAllProduct", query = "select new com.event.wrapper.ProductWrapper(p.id,p.name,p.description,p.location,p.price,p.category.id,p.category.name, p.imgUrl) from Product p")

@NamedQuery(name = "Product.updateProductStatus", query = "update Product p set p.status=:status where p.id=:id")

@NamedQuery(name = "Product.getProductByCategory", query = "select new com.event.wrapper.ProductWrapper(p.id, p.name) from Product p where p.category.id=:id and p.status='true'")

@NamedQuery(name = "Product.getProductById", query = "select new com.event.wrapper.ProductWrapper(p.id, p.name, p.description, p.price, p.imgUrl) from Product p where p.id=:id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Product implements Serializable {

    public static final Long serialVersionUId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    private String description;
    private String location;
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    private String status;

    private String imgUrl;

}


