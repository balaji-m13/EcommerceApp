package com.cloud.ecommerce.resourceserver.controller;

import com.cloud.ecommerce.resourceserver.model.orderAggregate.Order;
import com.cloud.ecommerce.resourceserver.service.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    @Autowired
    private PaymentService paymentService;
    @PostMapping("/webhook")
    public ResponseEntity<String> stripeWebHook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event = null;
        try{
            event = Webhook.constructEvent(payload, sigHeader, "whsec_7d6a043d734c3a3778d689b5230e48579980c9baaf4c183c975a5fdf93b41b95");

        } catch (SignatureVerificationException e) {
            System.out.println("Failed signature verification");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if(dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
            System.out.println(stripeObject.toJson());
        }
        else {

        }
        PaymentIntent intent;
        Order order;
        String intentId = "";
        switch (event.getType()) {
            case "payment_intent.succeeded":
                intent = (PaymentIntent) event.getData().getObject();
                System.out.println("payment OK");
                intentId = intent.getId();
                System.out.println("intentId:" + intentId);
                paymentService.updateOrderPaymentSucceeded(intentId);
                break;
            case "payment_intent.failed":
                intent = (PaymentIntent) event.getData().getObject();
                System.out.println("payment NOK");
                intentId = intent.getId();
                System.out.println("intentId:" + intentId);
                paymentService.updateOrderPaymentSucceeded(intentId);
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
