package com.familywebshop.stylet.repository.product;

import com.familywebshop.stylet.model.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByParent(Category parent);

    Optional<Category> findByName(String rootCategoryName);
}
