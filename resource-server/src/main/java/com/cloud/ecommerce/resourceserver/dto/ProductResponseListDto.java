package com.cloud.ecommerce.resourceserver.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductResponseListDto {
    private int pageSize;
    private Long totalCount;
    private int pageIndex;
    private List<ProductResponseDto> productList;

    public ProductResponseListDto() {
        this.productList = new ArrayList<>();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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
