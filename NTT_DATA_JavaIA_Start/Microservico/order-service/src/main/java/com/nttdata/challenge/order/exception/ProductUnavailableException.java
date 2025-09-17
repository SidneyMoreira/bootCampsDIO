package com.nttdata.challenge.order.exception;

public class ProductUnavailableException extends RuntimeException {

    public ProductUnavailableException(Long id) {
        super("Product with id " + id + " is unavailable");
    }
}
