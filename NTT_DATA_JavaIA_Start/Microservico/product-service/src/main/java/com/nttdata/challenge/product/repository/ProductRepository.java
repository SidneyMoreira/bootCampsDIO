package com.nttdata.challenge.product.repository;

import com.nttdata.challenge.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
