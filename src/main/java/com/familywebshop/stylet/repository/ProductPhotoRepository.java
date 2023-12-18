package com.familywebshop.stylet.repository;

import com.familywebshop.stylet.model.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {
}
