package com.cloud.ecommerce.resourceserver.model.orderAggregate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "deliverymethods")
public class DeliveryMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryMethodId")
    private Integer deliveryMethodId;
    @Column(nullable = false)
    private String shortName;
    @Column(nullable = false)
    private String deliveryTime;
    private String description;
    @Column(nullable = false)
    private long price;

    public DeliveryMethod() {}


    public DeliveryMethod(String shortName, String deliveryTime, String description, long price) {
        super();
        this.shortName = shortName;
        this.deliveryTime = deliveryTime;
        this.description = description;
        this.price = price;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deliveryMethod", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Order> orders;

    public Integer getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Integer deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
