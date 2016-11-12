package com.woact.dolplads.quiz.rest.backend.repository;

import com.woact.dolplads.quiz.rest.backend.annotations.Repository;
import com.woact.dolplads.quiz.rest.backend.entity.Vote;

import java.util.List;

/**
 * Created by dolplads on 12/10/2016.
 * Extension of the crudrepository
 * manages userspesific DB handling
 */
@Repository
@SuppressWarnings("unchecked")
public class VoteRepository extends CrudRepository<Long, Vote> {
    public VoteRepository() {
        super(Vote.class);
    }

    public Vote findByUserAndPost(String userName, Long postId) {
        List<Vote> votes = entityManager
                .createNamedQuery(Vote.VOTES_BY_POST_AND_USER)
                .setParameter("postId", postId)
                .setParameter("userName", userName)
                .getResultList();

        if (!votes.isEmpty()) {
            return votes.get(0);
        }

        return null;
    }
}

