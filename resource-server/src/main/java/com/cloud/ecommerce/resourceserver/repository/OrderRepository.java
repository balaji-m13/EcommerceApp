package com.cloud.ecommerce.resourceserver.repository;

import com.cloud.ecommerce.resourceserver.model.orderAggregate.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    public List<Order> findAllByBuyerEmail(String buyerEmail);
    public Order findByOrderIdAndBuyerEmail(Integer orderId, String buyerEmail);
    public Order findByPaymentIntentId(String paymentIntentId);

}
