package com.woact.dolplads.exam2016.backend.repository;

import com.woact.dolplads.exam2016.backend.annotations.Repository;
import com.woact.dolplads.exam2016.backend.entity.AbstractPost;
import com.woact.dolplads.exam2016.backend.entity.Comment;
import com.woact.dolplads.exam2016.backend.entity.Post;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by dolplads on 12/10/2016.
 * Extension of the crudrepository
 * manages userspesific DB handling
 */
@Repository
@SuppressWarnings("unchecked")
public class PostRepository extends CrudRepository<Long, Post> {
    public PostRepository() {
        super(Post.class);
    }

    public List<Post> findAllPostsByTime() {
        return entityManager.createNamedQuery(Post.POSTS_BY_CREATION_DATE)
                .getResultList();
    }

    public List<Post> findAllPostsByScore() {
        return entityManager
                .createNamedQuery(Post.POSTS_BY_SCORE)
                .getResultList();
    }

    public List<Comment> findCommentsByPost(Long requestedPostId) {
        return entityManager
                .createNamedQuery(Post.COMMENTS_BY_POST)
                .setParameter("postId", requestedPostId)
                .getResultList();
    }
}
