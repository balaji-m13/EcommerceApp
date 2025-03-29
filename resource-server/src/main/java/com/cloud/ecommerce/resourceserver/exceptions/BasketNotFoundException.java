package com.cloud.ecommerce.resourceserver.exceptions;

public class BasketNotFoundException extends Exception{
    public BasketNotFoundException(String message) {
        super(message);
    }
}
