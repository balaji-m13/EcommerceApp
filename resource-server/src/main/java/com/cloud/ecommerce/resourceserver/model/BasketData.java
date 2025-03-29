package com.cloud.ecommerce.resourceserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import lombok.Data;

import java.util.List;

@Data
public class BasketData {
    @Nonnull
    private String id;
    private List<BasketItem> Items;
    private Integer deliveryMethodId;
    private float shippingPrice;
    public BasketData() {

    }

    @Nonnull
    public String getId() {
        return id;
    }

    public void setId(@Nonnull String id) {
        this.id = id;
    }

    public List<BasketItem> getItems() {
        return Items;
    }

    public void setItems(List<BasketItem> items) {
        Items = items;
    }

    public Integer getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Integer deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public float getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(float shippingPrice) {
        this.shippingPrice = shippingPrice;
    }
}
