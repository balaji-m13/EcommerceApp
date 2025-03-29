package com.cloud.ecommerce.resourceserver.controller;

import com.cloud.ecommerce.resourceserver.model.orderAggregate.DeliveryMethod;
import com.cloud.ecommerce.resourceserver.repository.DeliveryMethodRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/deliverymethods")
public class DeliveryMethodController {
    @Autowired
    private DeliveryMethodRepository deliveryMethodRepository;

    @GetMapping
    public ResponseEntity<List<DeliveryMethod>> getDeliveryMethods() {
        List<DeliveryMethod> deliveryMethods = deliveryMethodRepository.findAll();
        if(deliveryMethods != null) {
            return new ResponseEntity<>(deliveryMethods, HttpStatus.OK);
        }
        else
            return ResponseEntity.notFound().build();
    }
}
