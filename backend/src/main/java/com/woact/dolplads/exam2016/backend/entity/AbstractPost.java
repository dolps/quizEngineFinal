package com.woact.dolplads.exam2016.backend.entity;

import com.woact.dolplads.exam2016.backend.annotations.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dolplads on 17/10/2016.
 */

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class AbstractPost {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn
    private User user;

    @NotEmpty
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Vote> votes;

    private int score;
    @Transient
    private int voteValueByUser;

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

    // TODO: 17/10/2016 check if necassary
    @PostConstruct
    public void init() {
        votes = new ArrayList<>();
    }

    @PreUpdate // TODO: 17/10/2016 test this
    public void updateScore() {
        int score = 0;
        for (Vote vote : this.votes) {
            score += vote.getVoteValue();
        }
    }
}
