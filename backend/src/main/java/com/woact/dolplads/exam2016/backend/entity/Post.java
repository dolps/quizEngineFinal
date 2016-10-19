package com.woact.dolplads.exam2016.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dolplads on 18/10/2016.
 * <p>
 * Simular too Comment so they both inherit from the abstract Post
 * But only the Posts have comments and only the comment can be moderated
 */
@NamedQueries({
        @NamedQuery(name = Post.POSTS_BY_CREATION_DATE,
                query = "select post from Post post order by (post.creationDate) desc"),
        @NamedQuery(name = Post.POSTS_BY_SCORE, query = "select post from Post post order by (post.score) desc"),
        @NamedQuery(name = Post.COMMENTS_BY_POST, query = "select post.comments from Post post where post.id = :postId")
})
@Entity
@Getter
@Setter
public class Post extends AbstractPost {
    public static final String POSTS_BY_CREATION_DATE = "post_by_creation_date";
    public static final String POSTS_BY_SCORE = "post_by_score";
    public static final String COMMENTS_BY_POST = "post_comment_by_post";
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
