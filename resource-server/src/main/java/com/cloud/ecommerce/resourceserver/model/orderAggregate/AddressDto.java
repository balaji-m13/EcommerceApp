package com.cloud.ecommerce.resourceserver.model.orderAggregate;

import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
public class AddressDto {
    private @Nonnull String firstName;
    private @Nonnull String lastName;
    private @Nonnull String street;
    private @Nonnull String city;
    private @Nonnull String state;
    private @Nonnull String zipCode;

    @Nonnull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Nonnull String firstName) {
        this.firstName = firstName;
    }

    @Nonnull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@Nonnull String lastName) {
        this.lastName = lastName;
    }

    @Nonnull
    public String getStreet() {
        return street;
    }

    public void setStreet(@Nonnull String street) {
        this.street = street;
    }

    @Nonnull
    public String getState() {
        return state;
    }

    public void setState(@Nonnull String state) {
        this.state = state;
    }

    @Nonnull
    public String getCity() {
        return city;
    }

    public void setCity(@Nonnull String city) {
        this.city = city;
    }

    @Nonnull
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(@Nonnull String zipCode) {
        this.zipCode = zipCode;
    }
}
