package com.woact.dolplads.entity;

import com.woact.dolplads.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by dolplads on 12/10/2016.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 40)
    @NotEmpty
    private String name;

    @Size(min = 3, max = 40)
    @NotEmpty
    private String surName;

    @NotNull
    private Address address;

    protected User() {
    }

    public User(String name, String surName, Address address) {
        this.name = name;
        this.surName = surName;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
