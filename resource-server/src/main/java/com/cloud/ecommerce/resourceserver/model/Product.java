package com.cloud.ecommerce.resourceserver.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "product")
@Data
public class Product {
    public Product(Category category, Brand brand,String sku, String title, String description, double unitPrice, String imageUrl,
                   boolean active, int unitsInStock, Date dateCreated, Date lastUpdated) {
        this.sku = sku;
        this.title = title;
        this.description = description;
        this.unitPrice = unitPrice;
        this.imageUrl = imageUrl;
        this.active = active;
        this.unitsInStock = unitsInStock;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
        this.category = category;
        this.brand = brand;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;

    @Column(name = "sku")
    private String sku;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "unitPrice")
    private double unitPrice;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "active")
    private boolean active;

    @Column(name = "unitsInStock")
    private int unitsInStock;

    @Column(name = "dateCreated")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "lastUpdated")
    @UpdateTimestamp
    private Date lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brandId")
    private Brand brand;

    public Product() {

    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
