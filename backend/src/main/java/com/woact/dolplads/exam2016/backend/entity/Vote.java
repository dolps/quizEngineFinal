package com.woact.dolplads.exam2016.backend.entity;

import javax.persistence.*;

/**
 * Created by dolplads on 18/10/2016.
 */
@NamedQueries({
        @NamedQuery(name = Vote.VOTES_BY_POST_AND_USER,
                query = "select vote from Vote vote where vote.postId = :postId and vote.user.userName = :userName")
})
@Entity
public class Vote {
    public static final String VOTES_BY_POST_AND_USER = "vote_by_post_user";
    @Id
    @GeneratedValue
    private Long id;
    private int value;

    @ManyToOne
    @JoinColumn
    private User user;

    private Long postId;


    public Vote() {
    }

    public Vote(User user, Long postId, int value) {
        this.user = user;
        this.value = value;
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
