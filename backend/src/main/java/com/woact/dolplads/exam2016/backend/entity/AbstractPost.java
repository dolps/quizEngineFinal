package com.woact.dolplads.exam2016.backend.entity;

import com.woact.dolplads.exam2016.backend.annotations.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by dolplads on 17/10/2016.
 */

@MappedSuperclass
@Getter
@Setter
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
}
