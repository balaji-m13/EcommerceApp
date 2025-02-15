package com.cloud.ecommerce.resourceserver.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Category {
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long categoryId;

    public Category(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @Column(name = "categoryName")
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Product> products;

    public Category() {

    }
}
