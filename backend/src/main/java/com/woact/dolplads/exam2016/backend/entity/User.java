package com.woact.dolplads.exam2016.backend.entity;

import com.woact.dolplads.exam2016.backend.annotations.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by dolplads on 12/10/2016.
 * Represents a user
 */
@NamedQueries({
        @NamedQuery(name = User.FIND_BY_USERNAME, query = "select user from User user where userName=:userName"),
        @NamedQuery(name = User.FIND_BY_CREDENTIALS,
                query = "select u from User u where u.userName = :userName and u.passwordHash = :password")
})
@Getter
@Setter
@Entity
public class User {
    public static final String FIND_BY_USERNAME = "user_find_by_username";
    public static final String FIND_BY_CREDENTIALS = "user_find_by_credentials";

    @Id
    @Pattern(regexp = "[A-Za-z0-9]{1,32}")
    @Column(unique = true, updatable = false) // !updateable == so we dont loose consistency in DB
    @Size(max = 25)
    @NotEmpty
    private String userName;

    @Size(max = 125)
    @NotEmpty
    private String firstName;

    @NotNull // TODO: 17/10/2016 check if necassary
    @Size(max = 125)
    private String middleName;

    @Size(max = 125)
    @NotEmpty
    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @NotEmpty
    private String passwordHash;

    @NotEmpty
    private String salt;

    @Transient
    private boolean loggedIn;

    public User() {
    }

    public User(String userName, String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userName = userName;
    }

    @PrePersist
    public void preparePersist() {
        this.registrationDate = new Date();
    }
}
