package com.woact.dolplads.exam2016.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by dolplads on 18/10/2016.
 */
@NamedQueries({
        @NamedQuery(name = Vote.VOTES_BY_POST_AND_USER,
                query = "select vote from Vote vote where vote.postId = :postId and vote.user.userName = :userName")
})
@Entity
@Getter
@Setter
@NoArgsConstructor
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

    public Vote(User user, Long postId, int value) {
        this.user = user;
        this.value = value;
        this.postId = postId;
    }
}
