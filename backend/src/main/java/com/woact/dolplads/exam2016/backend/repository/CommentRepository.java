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

    public List<Comment> findByPost(Long postId) {
        return entityManager.createQuery("select comment from Comment comment where comment.post.id = :postId")
                .setParameter("postId", postId)
                .getResultList();
    }

    public List<Comment> findByUser(String userName) {
        return entityManager.createQuery("select comment from Comment comment where comment.user.userName = :userName")
                .setParameter("userName", userName)
                .getResultList();
    }
}
