package com.cloud.ecommerce.resourceserver.dto;

import com.cloud.ecommerce.resourceserver.model.Product;
import lombok.Data;

@Data
public class ProductResponseDto {
    private Long productId;
    private double unitPrice;
    private Long brandId;
    private Long categoryId;
    private String name;
    private String sku;
    private String description;
    private String categoryName;
    private String brandName;
    private String imageUrl;

    public void populateDto(Product product) {
        this.productId = product.getProductId();
        this.categoryId = product.getCategory().getCategoryId();
        this.unitPrice = product.getUnitPrice();
        this.brandId = product.getBrand().getBrandId();
        this.name = product.getTitle();
        this.sku = product.getSku();
        this.description = product.getDescription();
        this.categoryName = product.getCategory().getCategoryName();
        this.brandName = product.getBrand().getBrandName();
        this.imageUrl = product.getImageUrl();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
