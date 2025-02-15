package com.cloud.ecommerce.resourceserver.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductResponseListDto {
    private int totalPages;
    private Long totalCount;
    private int pageIndex;
    private List<ProductResponseDto> productList;

    public ProductResponseListDto() {
        this.productList = new ArrayList<>();
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public List<ProductResponseDto> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductResponseDto> productList) {
        this.productList = productList;
    }
}
