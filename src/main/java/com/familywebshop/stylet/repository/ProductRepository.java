package com.familywebshop.stylet.repository;

import com.familywebshop.stylet.model.Category;
import com.familywebshop.stylet.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll(Specification<Product> spec, Sort sort);

}
