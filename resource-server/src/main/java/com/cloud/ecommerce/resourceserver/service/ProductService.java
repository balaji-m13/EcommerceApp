package com.cloud.ecommerce.resourceserver.service;

import com.cloud.ecommerce.resourceserver.dto.ProductResponseDto;
import com.cloud.ecommerce.resourceserver.dto.ProductResponseListDto;
import com.cloud.ecommerce.resourceserver.model.Product;
import com.cloud.ecommerce.resourceserver.repository.CategoryRepository;
import com.cloud.ecommerce.resourceserver.repository.ProductRepository;
import com.cloud.ecommerce.resourceserver.specification.ProductSpecParams;
import com.cloud.ecommerce.resourceserver.specification.ProductSpecificationTitleBrandCategory;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{

    @Value("${pagination.page.size.default}")
    private Integer defaultPageSize;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductSpecificationTitleBrandCategory productSpecification;


    @Override
    public ProductResponseDto getProductById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if(productOpt.isPresent()) {
            ProductResponseDto productResponseDto = new ProductResponseDto();
            productResponseDto.populateDto(productOpt.get());
            return productResponseDto;
        }
        return null;
    }

    @Override
    public ProductResponseListDto getProductList(ProductSpecParams specParams) {
        List<Product> productList = null;
        Page<Product> pages = null;
        if(Integer.valueOf(specParams.getPageIndex()) == null || Integer.valueOf(specParams.getPageIndex()) == 0) {
            productList = productRepository.findAll(productSpecification.getProducts(specParams));
            if(productList != null && productList.size() > 0) {
                ProductResponseListDto productResponseListDto = new ProductResponseListDto();
                productResponseListDto.setTotalCount(Long.valueOf(productList.size()));
                productResponseListDto.setProductList(new ArrayList<>());
                for(Product product: productList) {
                    ProductResponseDto productResponseDto = new ProductResponseDto();
                    productResponseDto.populateDto(product);
                    productResponseListDto.getProductList().add(productResponseDto);
                }
                return productResponseListDto;
            }
        }
        else {
            if(Integer.valueOf(specParams.getPageSize()) == null || specParams.getPageSize() == 0) {
                specParams.setPageSize(defaultPageSize);
            }
            Pageable paging = PageRequest.of(specParams.getPageIndex() - 1, specParams.getPageSize());
            pages = productRepository.findAll(productSpecification.getProducts(specParams), paging);
            System.out.println("PAGES");
            if(pages != null && pages.getContent() != null) {
                productList = pages.stream().toList();
                System.out.println("Product List");
                System.out.println(productList.size());
                if(productList != null && productList.size() > 0) {
                    System.out.println("Product list not null");
                    ProductResponseListDto productResponseListDto = new ProductResponseListDto();
                    productResponseListDto.setTotalCount(pages.getTotalElements());
                    productResponseListDto.setPageIndex(pages.getNumber() + 1);
                    productResponseListDto.setPageSize(specParams.getPageSize());
                    productResponseListDto.setProductList(new ArrayList<>());
                    for(Product product: productList) {
                        ProductResponseDto productResponseDto = new ProductResponseDto();
                        productResponseDto.populateDto(product);
                        productResponseListDto.getProductList().add(productResponseDto);
                    }
                    System.out.println(productResponseListDto.getPageSize());
                    return productResponseListDto;
                }
            }
        }
        return null;
    }
}
