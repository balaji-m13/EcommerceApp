package com.cloud.ecommerce.resourceserver.specification;


import lombok.Data;

@Data
public class ProductSpecParams {

    private int pageSize;
    private String search;
    private String sort;
    private int brandId;
    private int categoryId;
    private String title;
    private final int MaxPageSize=20;
    private int pageIndex=1;
    private double minPrice = -1.0;
    private double maxPrice = -1.0;

    public void setPageSize(int pageSize) {
        if(pageSize > MaxPageSize) {
            this.pageSize= MaxPageSize;
        }
        this.pageSize = pageSize;
    }

    public void setSearch(String search) {
        this.search = search.toLowerCase();
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getSearch() {
        return search;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxPageSize() {
        return MaxPageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
