package com.cloud.ecommerce.resourceserver.repository;

import com.cloud.ecommerce.resourceserver.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
