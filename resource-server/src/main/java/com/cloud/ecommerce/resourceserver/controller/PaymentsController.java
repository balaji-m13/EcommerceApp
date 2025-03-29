package com.cloud.ecommerce.resourceserver.controller;

import com.cloud.ecommerce.resourceserver.exceptions.BasketNotFoundException;
import com.cloud.ecommerce.resourceserver.exceptions.DeliveryMethodNotFoundException;
import com.cloud.ecommerce.resourceserver.exceptions.ProductNotFoundException;
import com.cloud.ecommerce.resourceserver.model.Basket;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.Order;
import com.cloud.ecommerce.resourceserver.service.IPaymentInterface;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
    @Autowired
    private IPaymentInterface paymentService;

    @PostMapping("/{basketId}")
    public ResponseEntity<Basket> createOrUpdatePaymentIntent(@PathVariable("basketId") String basketId) throws StripeException, BasketNotFoundException, DeliveryMethodNotFoundException, ProductNotFoundException {
        Basket basket = paymentService.createOrUpdatePaymentIntent(basketId);
        if(basket == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(basket);
    }
}
