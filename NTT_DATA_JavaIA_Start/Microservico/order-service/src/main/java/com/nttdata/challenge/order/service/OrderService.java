package com.nttdata.challenge.order.service;

import com.nttdata.challenge.order.client.ProductCatalogClient;
import com.nttdata.challenge.order.dto.OrderItemRequest;
import com.nttdata.challenge.order.dto.OrderItemResponse;
import com.nttdata.challenge.order.dto.OrderRequest;
import com.nttdata.challenge.order.dto.OrderResponse;
import com.nttdata.challenge.order.dto.ProductSummary;
import com.nttdata.challenge.order.exception.ProductUnavailableException;
import feign.FeignException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final ProductCatalogClient productCatalogClient;

    public OrderService(ProductCatalogClient productCatalogClient) {
        this.productCatalogClient = productCatalogClient;
    }

    public List<ProductSummary> listCatalog() {
        return productCatalogClient.listProducts();
    }

    public OrderResponse simulateOrder(OrderRequest request) {
        List<OrderItemResponse> items = request.getItems().stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList());

        BigDecimal total = items.stream()
                .map(OrderItemResponse::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderResponse(UUID.randomUUID(), request.getCustomer(), Instant.now(), items, total);
    }

    private OrderItemResponse toItemResponse(OrderItemRequest itemRequest) {
        ProductSummary product = fetchProduct(itemRequest.getProductId());
        BigDecimal unitPrice = product.getPrice();
        BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
        return new OrderItemResponse(product.getId(), product.getName(), product.getDescription(), itemRequest.getQuantity(), unitPrice, lineTotal);
    }

    private ProductSummary fetchProduct(Long id) {
        try {
            return productCatalogClient.getProductById(id);
        } catch (FeignException.NotFound notFound) {
            throw new ProductUnavailableException(id);
        } catch (FeignException error) {
            throw new ProductUnavailableException(id);
        }
    }
}
