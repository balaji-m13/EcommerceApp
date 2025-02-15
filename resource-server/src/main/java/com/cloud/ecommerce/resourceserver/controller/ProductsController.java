package com.cloud.ecommerce.resourceserver.controller;

import com.cloud.ecommerce.resourceserver.dto.ProductResponseDto;
import com.cloud.ecommerce.resourceserver.dto.ProductResponseListDto;
import com.cloud.ecommerce.resourceserver.model.Brand;
import com.cloud.ecommerce.resourceserver.model.Category;
import com.cloud.ecommerce.resourceserver.repository.BrandRepository;
import com.cloud.ecommerce.resourceserver.repository.CategoryRepository;
import com.cloud.ecommerce.resourceserver.repository.ProductRepository;
import com.cloud.ecommerce.resourceserver.service.IProductService;
import com.cloud.ecommerce.resourceserver.specification.ProductSpecParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private IProductService productService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories != null) {
            return ResponseEntity.ok(categories);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/brands")
    public ResponseEntity<List<Brand>> getBrands() {
        List<Brand> brands = brandRepository.findAll();
        System.out.println("BRAND DB RESPONSE");
        System.out.println(brands.size());
        for(int i = 0; i < brands.size(); i++) {
            System.out.println(brands.get(i).getBrandId() + " " + brands.get(i).getBrandName());
        }
        if(brands != null)
            return ResponseEntity.ok(brands);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products")
    public ResponseEntity<ProductResponseListDto> getProducts(@ModelAttribute ProductSpecParams request) {
        System.out.println("Incoming request params");
        System.out.println(request.getCategoryId());
        System.out.println(request.getBrandId());
        System.out.println(request.getPageIndex());
        System.out.println(request.getPageSize());
        System.out.println(request.getSearch());
        ProductResponseListDto productList = productService.getProductList(request);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        ProductResponseDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
}
