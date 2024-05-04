package org.example.nextcommerce.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@Embeddable
public class Address {

    private String zipCode;
    private String address;
    private String detailAddress;
    private String extraAddress;

    public Address(String zipCode, String address, String detailAddress, String extraAddress) {
        this.zipCode = zipCode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.extraAddress = extraAddress;
    }

    public Address() {

    }
}