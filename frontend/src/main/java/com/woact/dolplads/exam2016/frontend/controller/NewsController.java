package com.woact.dolplads.exam2016.frontend.controller;

import com.woact.dolplads.exam2016.backend.entity.Post;
import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.backend.entity.Vote;
import com.woact.dolplads.exam2016.backend.service.PostEJB;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by dolplads on 17/10/2016.
 */

@Log
@SessionScoped
@Named
@Getter
@Setter
public class NewsController implements Serializable {
    @Inject
    private LoginController loginController;
    @EJB
    private PostEJB postEJB;
    private Post post;

    private int someVal = 1;
    private String sortValue = "time";
    private String voteValue;

    private String newsSortOption;

    @PostConstruct
    public void doConstruct() {
        post = new Post();
        newsSortOption = "BYSCORE";
    }

    public void updatePosts() {
        log.log(Level.INFO, "sortoption: " + newsSortOption);
    }

    /**
     * This is working now i think
     *
     * @param event
     */
    public void valueChanged(ValueChangeEvent event) {
        log.log(Level.INFO, "fired event " + event.toString());
        log.log(Level.INFO, "vote value " + sortValue);
        //do your stuff
    }

    public String doCreate() {
        User current = loginController.getSessionUser();
        post.setUser(current);

        Post persisted = postEJB.createPost(post);

        if (persisted == null) {
            return null;
        }
        post = new Post();
        return "home.jsf";
    }

    public void updateVote(Post post, int value) {
        User user = loginController.getSessionUser();
        Long postId = post.getId();

        if (value == -1) {
            postEJB.voteAgainst(user.getUserName(), postId);
        } else if (value == 0) {
            postEJB.unVote(user.getUserName(), postId);
        } else {
            postEJB.voteForPost(user.getUserName(), postId);
        }
    }

    public List<Post> displayNews() {
        List<Post> posts;

        if (sortValue != null) {
            if (sortValue.equals("time")) {
                posts = postEJB.getAllPostByTime();
            } else {
                posts = postEJB.getAllPostsByScore();
            }
        } else {
            posts = postEJB.getAllPostByTime();
        }

        for (Post post : posts) {
            post.setScore(postEJB.getScoreForPost(post.getId()));
        }

        User sessionUser = loginController.getSessionUser();
        if (sessionUser != null) {
            setMyVote(sessionUser, posts);
        }

        return posts;
    }

    private void setMyVote(User sessionUser, List<Post> posts) {
        posts.forEach(post -> {
            List<Vote> votes = post.getVotes();
            votes.forEach(vote -> {
                boolean isEqual = vote.getUser().getUserName().equals(sessionUser.getUserName());
                if (isEqual) {
                    post.setVoteValueByUser(vote.getVoteValue());
                }
            });
        });
    }
}
