package com.cloud.ecommerce.resourceserver.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.ecommerce.resourceserver.model.Basket;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.Address;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.Order;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.OrderDto;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.OrderItem;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.OrderItemDto;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.OrderResponseDto;
import com.cloud.ecommerce.resourceserver.repository.BasketRepository;
import com.cloud.ecommerce.resourceserver.service.IOrderService;

@RestController
public class OrderController {

    Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    private IOrderService orderService;
    @Autowired
    private BasketRepository basketRepository;
    @PostMapping("/api/orders")
    public ResponseEntity<Order> createUpdateOrder(@RequestBody OrderDto orderDto, JwtAuthenticationToken auth)
            throws Exception {

        String email = auth.getToken().getClaimAsString("email");
        System.out.println("OrderController... creating an order....");
        Basket basket = null;
        Address address =  extractAddress(orderDto);
        Optional<Basket> basketOpt = basketRepository.findById(orderDto.getBasketId());
        if(basketOpt.isPresent()) {
            basket = basketOpt.get();
        }
        else throw new Exception("Order Controller: basket is not found....");

        System.out.println("Basket found....");
        System.out.println("Checking if order is already present in the db by using paymentIntentId");
        Order order = orderService.getOrderByPaymentId(basket.getPaymentIntentId());
        if(order != null) {
            System.out.println("Order exists...");
            orderService.updateOrder(order, basket, address);
        }
        else {
            System.out.println("Order does not exist, we're creating a new order");
            order = orderService.createOrder(email, orderDto.getDeliveryMethodId(), basket, address);
        }
        if(order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        else return ResponseEntity.notFound().build();
    }

    private Address extractAddress(OrderDto orderDto) {
        String firstName = orderDto.getShipToAddress().getFirstName();
        String lastName = orderDto.getShipToAddress().getLastName();
        String city = orderDto.getShipToAddress().getCity();
        String state = orderDto.getShipToAddress().getState();
        String street = orderDto.getShipToAddress().getStreet();
        String zipCode = orderDto.getShipToAddress().getZipCode();

        Address address = new Address(firstName,lastName, city,street,state, zipCode);
        return address;


    }
    @GetMapping("/api/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrdersForUser(JwtAuthenticationToken auth) {

        String email = auth.getToken().getClaimAsString("email");
        List<Order> orders = orderService.getOrdersForUser(email);
        List<OrderResponseDto> ordersReturned = new ArrayList<>();
        for(Order order: orders) {

            OrderResponseDto ordto = new OrderResponseDto();
            ordto.setOrderDate(order.getOrderDate());
            ordto.setOrderId(order.getOrderId());
            ordto.setStatus(order.getStatus().toString());
            ordto.setSubTotal(order.getSubTotal());
            ordto.setTotal(order.getTotal());
            ordto.setShippingPrice(order.getDeliveryMethod().getPrice());
            ordto.setDeliveryMethodShortName(order.getDeliveryMethod().getShortName());
            ordto.setOrderItems(null);
            ordersReturned.add(ordto);
        }
        return ResponseEntity.ok(ordersReturned);
    }

    @GetMapping("/api/orders/{id}")
    public ResponseEntity<OrderResponseDto> getOrderByIdForUser(@PathVariable int id, JwtAuthenticationToken auth) {
        String email = auth.getToken().getClaimAsString("email");
        Order order = orderService.getOrderById(id, email);
        if(order != null) {
            OrderResponseDto ordto = new OrderResponseDto();
            ordto.setOrderDate(order.getOrderDate());
            ordto.setOrderId(order.getOrderId());
            ordto.setStatus(order.getStatus().toString());
            ordto.setSubTotal(order.getSubTotal());
            ordto.setTotal(order.getTotal());
            ordto.setShippingPrice(order.getDeliveryMethod().getPrice());
            ordto.setDeliveryMethodShortName(order.getDeliveryMethod().getShortName());
            List<OrderItem> orderItems = order.getOrderItems();
            List<OrderItemDto> oidtoList = new ArrayList<>();
            for(OrderItem oi: orderItems) {
                OrderItemDto oidto = new OrderItemDto();
                oidto.setProductId(oi.getItemOrdered().getProductItemId());
                oidto.setProductName(oi.getItemOrdered().getProductName());
                oidto.setQuantity(oi.getQuantity());
                oidto.setUnitPrice(oi.getPrice());
                oidto.setImageUrl(oi.getItemOrdered().getImageUrl());
                oidtoList.add(oidto);
            }
            ordto.setOrderItems(oidtoList);
            return new ResponseEntity<>(ordto, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping(value="api/orders/test")
    public void logUsername(Authentication authentication, JwtAuthenticationToken auth) {
        logger.debug(authentication.getPrincipal().toString());
        logger.debug(auth.getToken().getClaimAsString("email"));
        if (auth.getToken().hasClaim("preferred_username")) {
            logger.debug(auth.getToken().getClaimAsString("preferred_username"));
        }
        System.out.println("roles....");
        authentication.getAuthorities().forEach(
                r->{System.out.println(r.getAuthority()); });



    }

}
