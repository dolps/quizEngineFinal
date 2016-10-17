package com.woact.dolplads.exam2016.backend.repository;

import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.backend.entity.Vote;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by dolplads on 17/10/2016.
 */
@Stateless
@SuppressWarnings("unchecked")
public class VoteRepository extends CrudRepository<Long, Vote> {
    VoteRepository() {
        super(Vote.class);
    }

    // TODO: 17/10/2016 THIS WILL NOT WORK
    public Vote findByUserAndComment(String userName, Long postId) {
        List<Vote> result = entityManager
                .createQuery("select vote from Vote vote where vote.user.userName = :userName and vote.post.id = :postId")
                .setParameter("userName", userName)
                .setParameter("postId", postId)
                .getResultList();

        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }
}
