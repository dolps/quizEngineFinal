package com.woact.dolplads.exam2016.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dolplads on 18/10/2016.
 */
@Entity
@Getter
@Setter
public class Post extends AbstractPost {
    @OneToMany
    private List<Comment> comments;

    public Post() {
    }

    public Post(User user, String text) {
        super(user, text);
    }

    @PostConstruct
    public void init() {
        comments = new ArrayList<>();
    }
}
