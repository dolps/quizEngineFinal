package com.woact.dolplads.exam2016.backend.repository;

import com.woact.dolplads.exam2016.backend.entity.Comment;

import javax.ejb.Stateless;
import java.util.List;

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
