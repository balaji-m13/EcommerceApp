package com.cloud.ecommerce.resourceserver.service;

import com.cloud.ecommerce.resourceserver.model.Basket;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.Address;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(String buyerEmail, Integer deliveryMethod, Basket basket, Address shipToAddress);
    List<Order> getOrdersForUser(String buyerEmail);
    Order getOrderById(Integer orderId, String buyerEmail);
    Order getOrderByPaymentId(String intentId);
    Order updateOrder(Order order, Basket basket, Address address);
}
