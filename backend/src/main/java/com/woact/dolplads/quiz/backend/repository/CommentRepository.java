package com.woact.dolplads.quiz.backend.repository;

import com.woact.dolplads.quiz.backend.entity.Comment;

import javax.ejb.Stateless;

/**
 * Created by dolplads on 17/10/2016.
 */
@SuppressWarnings("unchecked")
@Stateless
public class CommentRepository extends CrudRepository<Long, Comment> {
    public CommentRepository() {
        super(Comment.class);
    }
}
