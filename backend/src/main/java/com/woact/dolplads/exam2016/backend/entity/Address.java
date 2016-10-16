package com.woact.dolplads.entity;

import com.woact.dolplads.annotations.Country;
import com.woact.dolplads.enums.CountryEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by dolplads on 12/10/2016.
 */
@Embeddable
@Getter
@Setter
public class Address {
    private String street;

    private String post;

    @Enumerated(EnumType.STRING)
    @Country
    private CountryEnum countryEnum;

    public Address(String street, String post, CountryEnum countryEnum) {
        this.street = street;
        this.post = post;
        this.countryEnum = countryEnum;
    }

    public Address() {
    }
}
