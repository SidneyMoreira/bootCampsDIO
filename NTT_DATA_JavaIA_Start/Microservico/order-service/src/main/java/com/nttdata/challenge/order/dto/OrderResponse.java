package com.nttdata.challenge.order.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class OrderResponse {

    private UUID id;
    private String customer;
    private Instant createdAt;
    private List<OrderItemResponse> items;
    private BigDecimal total;

    public OrderResponse(UUID id, String customer, Instant createdAt, List<OrderItemResponse> items, BigDecimal total) {
        this.id = id;
        this.customer = customer;
        this.createdAt = createdAt;
        this.items = items;
        this.total = total;
    }

    public UUID getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
