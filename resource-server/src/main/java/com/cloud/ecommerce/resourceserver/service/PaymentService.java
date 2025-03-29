package com.cloud.ecommerce.resourceserver.service;

import com.cloud.ecommerce.resourceserver.exceptions.BasketNotFoundException;
import com.cloud.ecommerce.resourceserver.exceptions.DeliveryMethodNotFoundException;
import com.cloud.ecommerce.resourceserver.exceptions.ProductNotFoundException;
import com.cloud.ecommerce.resourceserver.model.Basket;
import com.cloud.ecommerce.resourceserver.model.BasketItem;
import com.cloud.ecommerce.resourceserver.model.Product;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.DeliveryMethod;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.Order;
import com.cloud.ecommerce.resourceserver.model.orderAggregate.OrderStatus;
import com.cloud.ecommerce.resourceserver.repository.BasketRepository;
import com.cloud.ecommerce.resourceserver.repository.DeliveryMethodRepository;
import com.cloud.ecommerce.resourceserver.repository.OrderRepository;
import com.cloud.ecommerce.resourceserver.repository.ProductRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentUpdateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentInterface {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private DeliveryMethodRepository deliveryMethodRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public Basket createOrUpdatePaymentIntent(String basketId) throws BasketNotFoundException, DeliveryMethodNotFoundException, ProductNotFoundException, StripeException {
        Stripe.apiKey = "sk_test_51QzJ1J03ZJeA9IYOIuAHcvsxbGXnkNynG4kAS4luiqBHc8hC4eMGurZYRxmNxoeYLIh2K7PO6fa8aGy5npBK2qLZ00wBUCKVya";
        Optional<Basket> basketOpt = basketRepository.findById(basketId);
        Basket basket = basketOpt.orElseThrow(()->  new BasketNotFoundException("basket with id" + basketId+ "not found"));

        long shippingPrice = 0L;
        //Let us get the deliverymethod selected
        if(basket.getDeliveryMethodId() != null) {
            Optional<DeliveryMethod> deliveryOpt = deliveryMethodRepository.findById(basket.getDeliveryMethodId());
            DeliveryMethod deliveryMethod = deliveryOpt.orElseThrow(() ->
                    new DeliveryMethodNotFoundException("deliverymethod with id"+ basket.getDeliveryMethodId()+ "not found"));
            shippingPrice = deliveryMethod.getPrice();
        }

        for(BasketItem item: basket.getItems()) {
            Optional<Product> productItemOpt = productRepository.findById(item.getProductId());
            Product productItem = null;
            if(productItemOpt.isPresent()) {
                productItem = productItemOpt.get();

            } else throw new ProductNotFoundException("Product with productId"+item.getProductId()+ "not found");
            if(item.getUnitPrice() != productItem.getUnitPrice()) {
                item.setUnitPrice(productItem.getUnitPrice());
            }
        }

        PaymentIntent intent;
        List<String> paymentTypes = new ArrayList<>();
        paymentTypes.add("card");
        if(isEmptyString(basket.getPaymentIntentId())) {
            System.out.println("Intent creating........");
            PaymentIntentCreateParams createParams =
                    PaymentIntentCreateParams.builder()
                            .setAmount(calculateOrderAmount(basket.getItems())+(long)shippingPrice*100)
                            .setCurrency("usd")
                            .addAllPaymentMethodType(paymentTypes)
                            .build();
            intent = PaymentIntent.create(createParams);
            basket.setPaymentIntentId(intent.getId());
            basket.setShippingPrice(shippingPrice);
            basket.setClientSecret(intent.getClientSecret());
        }
        else {
            intent = PaymentIntent.retrieve(basket.getPaymentIntentId());
            System.out.println("Intent updating.......:"+intent.getId());

            PaymentIntentUpdateParams updateParams =
                    PaymentIntentUpdateParams.builder()
                            .setAmount(calculateOrderAmount(basket.getItems())+ (long)shippingPrice*100)
                            .build();

            System.out.println(basket.getPaymentIntentId());
            intent.update(updateParams);
            basket.setShippingPrice(shippingPrice);


        }

        basketRepository.save(basket);
        return basket;
    }

    @Override
    public Order updateOrderPaymentSucceeded(String paymentIntentId) {
        Order order = orderRepository.findByPaymentIntentId(paymentIntentId);
        if(order == null)
                return null;
        order.setStatus(OrderStatus.PAYMENT_RECEIVED);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order updateOrderPaymentFailed(String paymentIntentId) {
        Order order = orderRepository.findByPaymentIntentId(paymentIntentId);
        if(order == null)
            return null;
        order.setStatus(OrderStatus.PAYMENT_FAILED);
        orderRepository.save(order);
        return order;
    }

    private boolean isEmptyString(String data) {
        return data == null || data.isEmpty();
    }

    private long calculateOrderAmount(List<BasketItem> items) {
        long sum = items.stream()
                .mapToLong(x -> (long) x.getUnitPrice() * 100 * x.getQuantity())
                .sum();
        return sum;
    }
}
