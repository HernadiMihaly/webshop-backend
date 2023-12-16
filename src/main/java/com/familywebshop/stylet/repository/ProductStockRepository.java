package com.familywebshop.stylet.repository;

import com.familywebshop.stylet.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    List<ProductStock> findByProductId(Long productId);

}
