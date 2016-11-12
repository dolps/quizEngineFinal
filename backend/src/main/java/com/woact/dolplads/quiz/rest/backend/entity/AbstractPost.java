package com.woact.dolplads.quiz.rest.backend.entity;

import com.woact.dolplads.quiz.rest.backend.annotations.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by dolplads on 17/10/2016.
 */

@MappedSuperclass
public abstract class AbstractPost {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Vote> votes;

    @Size(max = 125)
    @NotEmpty
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    private int score;

    protected AbstractPost() {
    }

    public AbstractPost(User user, String text) {
        this.user = user;
        this.text = text;
    }

    /**
     * Set the time of the first time this post was saved to DB
     */
    @PrePersist
    public void preparePersist() {
        if (creationDate == null) creationDate = new Date();
    }

    @PostLoad
    public void calculateScore() {
        setScore(getScore());
    }

    public int getScore() {
        int score = 0;
        for (Vote vote : votes) {
            score += vote.getValue();
        }
        return score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
