package com.cloud.ecommerce.resourceserver.model.orderAggregate;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class OrderResponseDto {
    private int orderId;
    private Instant orderDate;
    private String deliveryMethodShortName;
    private double total;
    private String status;
    private double subTotal;
    private double shippingPrice;
    private List<OrderItemDto> orderItems;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDeliveryMethodShortName() {
        return deliveryMethodShortName;
    }

    public void setDeliveryMethodShortName(String deliveryMethodShortName) {
        this.deliveryMethodShortName = deliveryMethodShortName;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }
}
