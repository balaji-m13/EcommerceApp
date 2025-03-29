package com.cloud.ecommerce.resourceserver.model.orderAggregate;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Address {
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipCode;

    public Address() {}

    public Address(String firstName, String lastName, String street, String state, String city, String zipCode) {
        this.firstName = Objects.requireNonNull(firstName);
        this.lastName = Objects.requireNonNull(lastName);
        this.street = Objects.requireNonNull(street);
        this.state = Objects.requireNonNull(state);
        this.city = Objects.requireNonNull(city);
        this.zipCode = Objects.requireNonNull(zipCode);
    }

    public @Nonnull String getFirstName() {
        return firstName;
    }

    public @Nonnull String getLastName() {
        return lastName;
    }

    public @Nonnull String getStreet() {
        return street;
    }

    public @Nonnull String getCity() {
        return city;
    }

    public @Nonnull String getState() {
        return state;
    }

    public @Nonnull String getZipCode() {
        return zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass())
            return false;
        Address that = (Address) o;
        return firstName.equals(that.firstName)
                && lastName.equals(that.lastName)
                && street.equals(that.street)
                && city.equals(that.city)
                && state.equals(that.state)
                && zipCode.equals(that.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, street, city, state, zipCode);
    }
}
