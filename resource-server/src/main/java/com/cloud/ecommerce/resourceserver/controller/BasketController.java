package com.cloud.ecommerce.resourceserver.controller;

import com.cloud.ecommerce.resourceserver.model.Basket;
import com.cloud.ecommerce.resourceserver.model.BasketData;
import com.cloud.ecommerce.resourceserver.repository.BasketRepository;
import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/api/basket")
public class BasketController {
    @Autowired
    private BasketRepository basketRepository;

//    @PostMapping()
//    public ResponseEntity<Basket> createBasket(@RequestBody BasketData basketData) {
//        Basket basket = new Basket(basketData.getId());
//        basket.setItems(basketData.getItems());
//        basketRepository.save(basket);
//        return ResponseEntity.ok(basket);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasket(@PathVariable("id") String id) {
        Optional<Basket> basketOpt = basketRepository.findById(id);
        if(basketOpt.isPresent())
            return ResponseEntity.ok(basketOpt.get());
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBasket(@PathVariable("id") String id) {
        Optional<Basket> basketOpt = basketRepository.findById(id);
        if(basketOpt.isPresent()) {
            basketRepository.delete(basketOpt.get());
            return ResponseEntity.ok(id);
        }
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<Basket> createOrUpdateBasket(@RequestBody BasketData basketData) {
        Stripe.apiKey = "sk_test_51QzJ1J03ZJeA9IYOIuAHcvsxbGXnkNynG4kAS4luiqBHc8hC4eMGurZYRxmNxoeYLIh2K7PO6fa8aGy5npBK2qLZ00wBUCKVya";
        Optional<Basket> basketOpt = basketRepository.findById(basketData.getId());
        Basket basket = null;
        if(basketOpt.isPresent()) {
            System.out.println("Basket is present");
            basket = basketOpt.get();
        }
        else {
            System.out.println("Basket does not exist");
            basket = new Basket(basketData.getId());
        }
        basket.setItems(basketData.getItems());
        basket.setDeliveryMethodId(basketData.getDeliveryMethodId());
        basket.setShippingPrice(basketData.getShippingPrice());

        basketRepository.save(basket);
        return ResponseEntity.ok(basket);
    }
}
