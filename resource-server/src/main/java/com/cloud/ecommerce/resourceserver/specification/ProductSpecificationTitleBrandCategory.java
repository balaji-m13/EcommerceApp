package com.cloud.ecommerce.resourceserver.specification;

import com.cloud.ecommerce.resourceserver.model.Brand;
import com.cloud.ecommerce.resourceserver.model.Category;
import com.cloud.ecommerce.resourceserver.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

@Component
public class ProductSpecificationTitleBrandCategory {
    public Specification<Product> getProducts(ProductSpecParams specParams) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(specParams.getSearch() != null && !specParams.getSearch().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + specParams.getSearch().toLowerCase() + "%"));
            }
            if(specParams.getCategoryId() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("category"),
                        new Category(Long.valueOf(specParams.getCategoryId()))));
            }
            if(specParams.getBrandId() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("brand"),
                        new Brand(Long.valueOf(specParams.getBrandId()))));
            }
            if(specParams.getSort() != null && !specParams.getSort().isEmpty()) {
                switch (specParams.getSort()) {
                    case "priceAsc":
                        query.orderBy(criteriaBuilder.asc(root.get("unitPrice")));
                        break;
                    case "priceDesc":
                        query.orderBy(criteriaBuilder.desc(root.get("unitPrice")));
                        break;
                    default:
                        query.orderBy(criteriaBuilder.asc(root.get("title")));
                        break;
                }
            }
            if(specParams.getMinPrice() != -1.0 && specParams.getMaxPrice() != -1.0) {
                predicates.add(criteriaBuilder.between(root.get("unitPrice"), specParams.getMinPrice(), specParams.getMaxPrice()));
            }
            if(specParams.getMinPrice() != -1.0 && specParams.getMaxPrice() == -1.0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("unitPrice"), specParams.getMinPrice()));
            }
            if(specParams.getMaxPrice() != -1.0 && specParams.getMinPrice() == -1.0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("unitPrice"), specParams.getMaxPrice()));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
