package com.woact.dolplads.quiz.backend.entity;

import com.woact.dolplads.quiz.backend.annotations.NotEmpty;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dolplads on 12/10/2016.
 * Represents a user
 */
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
