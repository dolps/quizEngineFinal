package com.woact.dolplads.exam2016.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by dolplads on 18/10/2016.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vote {
    @Id
    @GeneratedValue
    private Long id;

    private int value;

    @ManyToOne
    @JoinColumn
    private User user;

    private Long postId;


    public Vote(User user, int value) {
        this.user = user;
        this.value = value;
    }
}
