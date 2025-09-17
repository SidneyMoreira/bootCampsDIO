package com.nttdata.challenge.order.client;

import com.nttdata.challenge.order.dto.ProductSummary;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductCatalogClient {

    @GetMapping("/products")
    List<ProductSummary> listProducts();

    @GetMapping("/products/{id}")
    ProductSummary getProductById(@PathVariable("id") Long id);
}
