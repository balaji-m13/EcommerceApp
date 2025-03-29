package com.cloud.ecommerce.resourceserver.service;

import com.cloud.ecommerce.resourceserver.model.Basket;
import com.cloud.ecommerce.resourceserver.model.BasketItem;
import com.cloud.ecommerce.resourceserver.model.Product;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.*;
import com.cloud.ecommerce.resourceserver.repository.BasketRepository;
import com.cloud.ecommerce.resourceserver.repository.DeliveryMethodRepository;
import com.cloud.ecommerce.resourceserver.repository.OrderRepository;
import com.cloud.ecommerce.resourceserver.repository.ProductRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Service
public class OrderService implements IOrderService {
    Logger logger = LogManager.getLogManager().getLogger(String.valueOf(OrderService.class));
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private DeliveryMethodRepository deliveryMethodRepository;
    @Override
    public Order createOrder(String buyerEmail, Integer deliveryMethodId, Basket basket, Address shipToAddress) {
        System.out.println("Creating an order...");
        Order order = new Order();
        OrderItem orderItem = null;
        System.out.println("Iterating over basket items...");
        List<OrderItem> orderItems = new ArrayList<>();
        for(BasketItem item: basket.getItems()) {
            Optional<Product> productOpt = productRepository.findById(item.getProductId());
            if(productOpt.isPresent()) {
                Product product = productOpt.get();
                ProductItemOrdered pio = new ProductItemOrdered(product.getProductId(), product.getTitle(), product.getImageUrl());
                orderItem = new OrderItem(pio, item.getQuantity(), product.getUnitPrice());
                orderItem.setOrder(order);
                orderItems.add(orderItem);
            }
        }
        Optional<DeliveryMethod> deliveyOpt = deliveryMethodRepository.findById(deliveryMethodId);
        DeliveryMethod deliveryMethod = null;
        if(deliveyOpt.isPresent()) {
            deliveryMethod = deliveyOpt.get();
        }
        System.out.println("Printing delivery method...");
        System.out.println(deliveryMethod.toString());
        double subTotal = orderItems.stream().mapToDouble(o -> o.getQuantity() * o.getPrice()).sum();
        System.out.println("SubTotal: " + subTotal);
        System.out.println("Payment intent id: " + basket.getPaymentIntentId());
        order.setBuyerEmail(buyerEmail);
        order.setDeliveryMethod(deliveryMethod);
        order.setOrderItems(orderItems);
        order.setShipToAddress(shipToAddress);
        order.setSubTotal(subTotal);
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentIntentId(basket.getPaymentIntentId());
        order.setOrderDate(Instant.now());
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> getOrdersForUser(String buyerEmail) {
        return orderRepository.findAllByBuyerEmail(buyerEmail);
    }

    @Override
    public Order getOrderById(Integer orderId, String buyerEmail) {
        return orderRepository.findByOrderIdAndBuyerEmail(orderId, buyerEmail);
    }

    @Override
    public Order getOrderByPaymentId(String intentId) {
        return orderRepository.findByPaymentIntentId(intentId);
    }

    @Override
    public Order updateOrder(Order order, Basket basket, Address address) {
        List<OrderItem> orderItems = new ArrayList<>();
        System.out.println("Updating order");
        for(BasketItem item: basket.getItems()) {
            Optional<Product> productOpt = productRepository.findById((long)item.getProductId());
            if(productOpt.isPresent()) {
                Product product = productOpt.get();
                ProductItemOrdered pio = new ProductItemOrdered(product.getProductId(), product.getTitle(), product.getImageUrl());
                OrderItem orderItem = new OrderItem(pio, item.getQuantity(), item.getUnitPrice());
                orderItem.setOrder(order);
                orderItems.add(orderItem);
            }
        }
        Optional<DeliveryMethod> deliveryOpt = deliveryMethodRepository.findById(basket.getDeliveryMethodId());
        DeliveryMethod deliveryMethod = null;
        if(deliveryOpt.isPresent()) {
            deliveryMethod = deliveryOpt.get();
        }
        System.out.println("Delivery method: " + deliveryMethod);
        double subTotal = orderItems.stream().mapToDouble(o -> o.getQuantity() * o.getPrice()).sum();
        order.setShipToAddress(address);
        order.setSubTotal(subTotal);
        order.setOrderItems(orderItems);
        orderRepository.save(order);
        return order;
    }
}
