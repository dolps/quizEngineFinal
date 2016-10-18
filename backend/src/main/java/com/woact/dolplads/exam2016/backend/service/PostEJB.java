package com.woact.dolplads.exam2016.backend.service;

import com.woact.dolplads.exam2016.backend.entity.*;
import lombok.extern.java.Log;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by dolplads on 17/10/2016.
 */
@Log
@SuppressWarnings("unchecked")
@Stateless
public class PostEJB {
    @PersistenceContext
    private EntityManager entityManager;


    public Post createPost(String userName, Post post) {
        User user = entityManager.find(User.class, userName);
        post.setUser(user);
        user.getPosts().add(post);
        entityManager.persist(post);

        return post;
    }

    public Comment createCommentForPost(Long postId, Comment comment) {
        Post post = entityManager.find(Post.class, postId);
        post.getComments().add(comment);
        entityManager.persist(comment);

        return comment;
    }

    public void voteForPost(String userName, Long postId, int value) {
        log.log(Level.INFO, "voted for post! " + postId + "with value " + value);
        User user = entityManager.find(User.class, userName);
        Post post = entityManager.find(Post.class, postId);
        Vote vote = null;

        if (user == null || post == null) {
            throw new IllegalArgumentException("user or post dont exists");
        }

        for (Vote v : post.getVotes()) {
            log.log(Level.INFO, "traktor" + v.getUser().getUserName());
            if (v.getUser().getUserName().equals(userName)) {
                vote = entityManager.find(Vote.class, v.getId());
                vote.setValue(value);
                break;
            }
        }
        if (vote == null) {
            Vote v = new Vote(user, value);
            v.setPostId(postId);
            entityManager.persist(v);
            post.getVotes().add(v);
        }
    }

    public Post findPost(Long id) {
        return entityManager.find(Post.class, id);
    }

    public void unVotePost(String userName, Long postId) {
        Post p = entityManager.find(Post.class, postId);
        p.removeVote(userName);
    }

    public List<Post> findAllPostsByTime() {
        return entityManager
                .createQuery("select post from Post post order by (post.creationDate) desc")
                .getResultList();
    }

    public List<Post> findAllPostsByScore() {
        return entityManager
                .createQuery("select post from Post post order by (post.score) desc", Post.class)
                .getResultList();
    }

    public List<Comment> findAllComments() {
        return entityManager.createQuery("select comment from Comment comment")
                .getResultList();
    }

    public void moderateComment(String userName, Long commentId, boolean value) {
        Comment comment = entityManager.find(Comment.class, commentId);
        if (comment.getUser().getUserName().equals(userName)) {
            comment.setModerated(value);
            voteForComment(userName, commentId, 0);
        }
    }

    public Comment findComment(Long commentId) {
        return entityManager.find(Comment.class, commentId);
    }

    public void voteForComment(String userName, Long commentId, int value) {
        User user = entityManager.find(User.class, userName);
        Comment comment = entityManager.find(Comment.class, commentId);
        Vote vote = null;

        if (user == null || comment == null) {
            throw new IllegalArgumentException("user or comment dont exists");
        }
        if (comment.isModerated()) {
            return;
        }

        for (Vote v : comment.getVotes()) {
            log.log(Level.INFO, "traktor" + v.getUser().getUserName());
            if (v.getUser().getUserName().equals(userName)) {
                vote = entityManager.find(Vote.class, v.getId());
                vote.setValue(value);
                break;
            }
        }
        if (vote == null) {
            Vote v = new Vote(user, value);
            v.setPostId(commentId);
            entityManager.persist(v);
            comment.getVotes().add(v);
        }
    }

    public int findVoteValueForPost(String userName, Long postId) {
        log.log(Level.INFO, "finding votes for post " + postId + "user " + userName);
        List<Vote> votes = entityManager
                .createQuery("select vote from Vote vote where vote.user.userName = :userName and vote.postId = :postId")
                .setParameter("userName", userName)
                .setParameter("postId", postId)
                .getResultList();

        if (!votes.isEmpty()) {
            return votes.get(0).getValue();
        }
        return 0;
    }

    public List<Comment> findCommentsByPost(Long requestedPostId) {
        return entityManager.createQuery("select post.comments from Post post where post.id = :postId")
                .setParameter("postId", requestedPostId)
                .getResultList();
    }

    public int findVoteValueForComment(String userName, Long commentId) {
        List<Vote> votes = entityManager
                .createQuery("select vote from Vote vote where vote.user.userName = :userName and vote.postId = :commentId")
                .setParameter("userName", userName)
                .setParameter("commentId", commentId)
                .getResultList();

        if (!votes.isEmpty()) {
            return votes.get(0).getValue();
        }
        return 0;
    }
}
