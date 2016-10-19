package com.woact.dolplads.exam2016.backend.entity;

import com.woact.dolplads.exam2016.backend.annotations.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dolplads on 12/10/2016.
 * Represents a user
 */

@Getter
@Setter
@Entity
public class User {
    @Id
    @Pattern(regexp = "[A-Za-z0-9]{1,32}")
    @Column(unique = true, updatable = false) // !updateable == so we dont loose consistency in DB
    @Size(max = 25)
    private String userName;

    @Size(max = 125)
    @NotEmpty
    private String firstName;

    @Size(max = 125)
    private String middleName;

    @Size(max = 125)
    @NotEmpty
    private String lastName;

    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @Size(max = 125)
    @NotEmpty
    private String passwordHash;

    @Size(max = 125)
    @NotEmpty
    private String salt;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Vote> votes;

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

    @PostConstruct
    public void init() {
        posts = new ArrayList<>();
        comments = new ArrayList<>();
        votes = new ArrayList<>();
    }

    @PrePersist
    public void preparePersist() {
        this.registrationDate = new Date();
    }
}
