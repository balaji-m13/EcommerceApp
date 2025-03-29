package com.cloud.ecommerce.resourceserver.model.orderAggregate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orderitems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemId")
    private Integer orderItemId;

    @Embedded
    private ProductItemOrdered itemOrdered;

    private int quantity;
    private double price;
    public OrderItem() {}

    public OrderItem(ProductItemOrdered itemOrdered, int quantity, double price) {
        this.itemOrdered = itemOrdered;
        this.quantity = quantity;
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne(cascade = {CascadeType.ALL})
    @JsonIgnore
    @JoinColumn(name="orderId", referencedColumnName ="orderId")
    private Order order;

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public ProductItemOrdered getItemOrdered() {
        return itemOrdered;
    }

    public void setItemOrdered(ProductItemOrdered itemOrdered) {
        this.itemOrdered = itemOrdered;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
