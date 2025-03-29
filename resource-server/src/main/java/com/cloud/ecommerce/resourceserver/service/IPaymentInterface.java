package com.cloud.ecommerce.resourceserver.service;

import com.cloud.ecommerce.resourceserver.exceptions.BasketNotFoundException;
import com.cloud.ecommerce.resourceserver.exceptions.DeliveryMethodNotFoundException;
import com.cloud.ecommerce.resourceserver.exceptions.ProductNotFoundException;
import com.cloud.ecommerce.resourceserver.model.Basket;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.Order;
import com.stripe.exception.StripeException;

public interface IPaymentInterface {
    Basket createOrUpdatePaymentIntent(String basketId) throws BasketNotFoundException, DeliveryMethodNotFoundException, ProductNotFoundException, StripeException;
    Order updateOrderPaymentSucceeded(String paymentIntentId);
    Order updateOrderPaymentFailed(String paymentIntentId);
}
