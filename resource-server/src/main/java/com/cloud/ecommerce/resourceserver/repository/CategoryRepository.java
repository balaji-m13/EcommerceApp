package com.cloud.ecommerce.resourceserver.repository;

import com.cloud.ecommerce.resourceserver.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
