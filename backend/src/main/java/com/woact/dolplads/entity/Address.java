package com.woact.dolplads.entity;

import com.woact.dolplads.constraints.Country;
import com.woact.dolplads.enums.CountryEnum;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by dolplads on 12/10/2016.
 */
@Embeddable
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public CountryEnum getCountryEnum() {
        return countryEnum;
    }

    public void setCountryEnum(CountryEnum countryEnum) {
        this.countryEnum = countryEnum;
    }
}
