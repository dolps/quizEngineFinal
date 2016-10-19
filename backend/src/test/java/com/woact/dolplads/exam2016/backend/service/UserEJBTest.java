package com.woact.dolplads.exam2016.backend.service;

import com.woact.dolplads.exam2016.backend.entity.*;
import com.woact.dolplads.exam2016.backend.testUtils.ArquillianTestHelper;
import com.woact.dolplads.exam2016.backend.testUtils.DeleterEJB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.Validator;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 12/10/2016.
 */
public class UserEJBTest extends ArquillianTestHelper {
    private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis());
    @EJB
    private UserEJB userEJB;
    @EJB
    private PostEJB postEJB;
    @EJB
    private DeleterEJB deleterEJB;
    @Inject
    private Validator validator;

    @After
    @Before
    public void setUp() throws Exception {
        deleterEJB.deleteEntities(Post.class);
        deleterEJB.deleteEntities(Comment.class);
        deleterEJB.deleteEntities(Vote.class);
        deleterEJB.deleteEntities(User.class);
    }

    @Test
    public void testKarmaWithModeration() throws Exception {
        User user = getValidUser();
        user = userEJB.save(user);
        assertTrue(user != null);

        Post post = new Post(user, "someText");
        post = postEJB.createPost(user.getUserName(), post);
        assertTrue(post.getId() != null);
        assertEquals(1, postEJB.findAllPostsByTime().size());

        Comment comment1 = new Comment(user, "comment1");
        Comment comment2 = new Comment(user, "comment2");
        Comment comment3 = new Comment(user, "comment3");
        Comment comment4 = new Comment(user, "comment4s");

        comment1 = postEJB.createCommentForPost(post.getId(), comment1);
        comment2 = postEJB.createCommentForPost(post.getId(), comment2);
        comment3 = postEJB.createCommentForPost(post.getId(), comment3);
        comment4 = postEJB.createCommentForPost(post.getId(), comment4);

        assertEquals(4, postEJB.findAllComments().size());
        assertEquals(post.getId(), postEJB.findPost(post.getId()).getId());
        assertEquals(1, postEJB.findAllPostsByTime().size());

        postEJB.voteForPost(user.getUserName(), post.getId(), -1);
        assertEquals(-1, userEJB.getCarmaPointsForUser(user.getUserName()));

        postEJB.voteForComment(user.getUserName(), comment1.getId(), 1);
        postEJB.voteForComment(user.getUserName(), comment2.getId(), 1);
        assertEquals(1, userEJB.getCarmaPointsForUser(user.getUserName()));

        postEJB.moderateComment(user.getUserName(), comment3.getId(), true);
        assertEquals(-9, userEJB.getCarmaPointsForUser(user.getUserName()));

        postEJB.moderateComment(user.getUserName(), comment4.getId(), true);
        assertEquals(-19, userEJB.getCarmaPointsForUser(user.getUserName()));

        User u2 = getValidUser();
        u2 = userEJB.save(u2);

        postEJB.voteForComment(u2.getUserName(), comment1.getId(), 1);
        assertEquals(-18, userEJB.getCarmaPointsForUser(user.getUserName()));
    }


    /**
     * Following tests are not described in exam document,
     * just for own sake during development
     */
    @Test
    public void save() throws Exception {
        User user = getValidUser();

        userEJB.save(user);
        boolean created = userEJB.findById(user.getUserName()) != null;

        assertTrue(created);
    }

    @Test
    public void find() throws Exception {
        User user = getValidUser();

        userEJB.save(user);

        User found = userEJB.findById(user.getUserName());
        assertNotNull(found);
    }

    @Test
    public void login() throws Exception {
        User user = getValidUser();
        user.setUserName("userName");
        user.setPasswordHash("passwordHash");

        userEJB.save(user);

        User loggedInUser = userEJB.login("userName", "passwordHash");
        assertTrue(loggedInUser.isLoggedIn());
    }


    private User getValidUser() {
        User u = new User("userName" + counter.incrementAndGet(), "user", "us", "userson");
        u.setPasswordHash("hash");
        u.setSalt("salt");
        assertEquals(0, validator.validate(u).size());

        return u;
    }


}