package com.cloud.ecommerce.resourceserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasketItem {
    private Long productId;
    private String title;
    private int quantity;
    private String imageUrl;
    private double unitPrice;
    private String brandName;
    private String categoryName;

    public BasketItem(Long productId, String title, int quantity, String imageUrl, double unitPrice, String brandName, String categoryName) {
        this.productId = productId;
        this.title = title;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.unitPrice = unitPrice;
        this.brandName = brandName;
        this.categoryName = categoryName;
    }
    public BasketItem() {}
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
