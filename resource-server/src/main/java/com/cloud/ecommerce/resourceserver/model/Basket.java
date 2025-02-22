package com.cloud.ecommerce.resourceserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@RedisHash("BASKET")
@Data
public class Basket {
    @Id
    private String id;
    private List<BasketItem> items;
    public Basket(String basketId) {
        this.id = basketId;
        items = new ArrayList<>();
    }
    public Basket() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void setItems(List<BasketItem> items) {
        this.items = items;
    }
}
