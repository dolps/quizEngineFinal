package com.woact.dolplads.entity;

import com.woact.dolplads.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by dolplads on 12/10/2016.
 * Represents a user
 */
@NamedQueries({
        @NamedQuery(name = User.FIND_BY_USERNAME, query = "select user from User user where userName=:userName")
})
@Entity
public class User {
    public static final String FIND_BY_USERNAME = "user_find_by_username";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 40)
    @NotEmpty
    private String name;

    @Size(min = 3, max = 40)
    @NotEmpty
    private String surName;

    @Size(min = 3, max = 12)
    @NotEmpty
    private String userName;

    @NotNull
    @Embedded
    private Address address;

    private String passwordHash;

    protected User() {
    }

    public User(String name, String surName, String userName, Address address) {
        this.name = name;
        this.surName = surName;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
