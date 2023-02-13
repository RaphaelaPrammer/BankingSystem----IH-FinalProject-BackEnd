package com.ironhack.bankingsystemapp.models.users;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String addressName;
    private String city;
    private String postalCode;
    private String country;

    public Address() {
    }

    public Address(String addressName, String city, String postalCode, String country) {
        this.addressName = addressName;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
