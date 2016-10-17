package com.woact.dolplads.exam2016.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by dolplads on 17/10/2016.
 */
// TODO: 17/10/2016 Change ID to combined id
@Entity
@Getter
@Setter
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private AbstractPost post;

    private int voteValue;

    protected Vote() {
    }

    public Vote(User user, AbstractPost post, int voteValue) {
        this.user = user;
        this.post = post;
        this.voteValue = voteValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vote vote = (Vote) o;

        return id != null ? id.equals(vote.id) : vote.id == null && user.equals(vote.user) && post.equals(vote.post);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + user.hashCode();
        result = 31 * result + post.hashCode();
        return result;
    }
}
