package com.woact.dolplads.exam2016.backend.repository;

import com.woact.dolplads.exam2016.backend.entity.Post;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by dolplads on 17/10/2016.
 */
@SuppressWarnings("unchecked")
@Stateless
public class PostRepository extends CrudRepository<Long, Post> {
    public PostRepository() {
        super(Post.class);
    }

    public List<Post> findAllByTime() {
        return entityManager.createQuery("select post from Post post order by post.creationDate desc")
                .getResultList();
    }

    public List<Post> findAllByScore() {
        return entityManager.createQuery("select vote.post from Vote vote order by vote.voteValue desc")
                .getResultList();
    }

    public List<Post> findByUser(String userName) {
        return entityManager.createQuery("select post from Post post where post.user.userName = :userName")
                .setParameter("userName", userName)
                .getResultList();
    }
}
