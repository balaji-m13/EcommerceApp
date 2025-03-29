package com.cloud.ecommerce.resourceserver.model.orderAggregate;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ProductItemOrdered {
    private long productItemId;
    private String productName;
    private String imageUrl;
    public ProductItemOrdered() {}

    public ProductItemOrdered(@Nonnull long productItemId, @Nonnull String productName, @Nonnull String imageUrl) {
        this.productItemId = Objects.requireNonNull(productItemId);
        this.productName = Objects.requireNonNull(productName);
        this.imageUrl = Objects.requireNonNull(imageUrl);
    }

    public @Nonnull long getProductItemId() {
        return productItemId;
    }

    public @Nonnull String getProductName() {
        return productName;
    }

    public @Nonnull String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productItemId, productName, imageUrl);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass())
            return false;
        ProductItemOrdered that = (ProductItemOrdered) o;
        return productItemId == that.productItemId
                && Objects.equals(imageUrl, that.imageUrl)
                && Objects.equals(productName, that.productName);
    }
}
