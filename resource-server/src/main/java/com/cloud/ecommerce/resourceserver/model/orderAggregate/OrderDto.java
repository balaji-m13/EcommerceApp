package com.cloud.ecommerce.resourceserver.model.orderAggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderDto {
    @JsonProperty("basketId")
    private String BasketId;

    @JsonProperty("deliveryMethodId")
    private int deliveryMethodId;

    @JsonProperty("shipToAddress")
    private AddressDto shipToAddress;


    public String getBasketId() {
        return BasketId;
    }

    public void setBasketId(String basketId) {
        BasketId = basketId;
    }

    public int getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(int deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public AddressDto getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(AddressDto shipToAddress) {
        this.shipToAddress = shipToAddress;
    }
}
