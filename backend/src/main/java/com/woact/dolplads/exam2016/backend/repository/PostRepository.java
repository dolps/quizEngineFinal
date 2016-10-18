package com.woact.dolplads.exam2016.backend.repository;

import com.woact.dolplads.exam2016.backend.entity.AbstractPost;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by dolplads on 17/10/2016.
 */
@SuppressWarnings("unchecked")
@Stateless
public class PostRepository extends CrudRepository<Long, AbstractPost> {
    public PostRepository() {
        super(AbstractPost.class);
    }

    public List<AbstractPost> findAllByTime() {
        return entityManager.createQuery("select post from Post post order by post.creationDate desc")
                .getResultList();
    }

    public List<AbstractPost> findByUser(String userName) {
        return entityManager.createQuery("select post from Post post where post.user.userName = :userName")
                .setParameter("userName", userName)
                .getResultList();
    }
}
