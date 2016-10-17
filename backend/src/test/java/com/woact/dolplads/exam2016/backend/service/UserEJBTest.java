package com.woact.dolplads.exam2016.backend.service;

import com.woact.dolplads.exam2016.backend.entity.Comment;
import com.woact.dolplads.exam2016.backend.entity.Post;
import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.backend.entity.Vote;
import com.woact.dolplads.exam2016.backend.testUtils.ArquillianTestHelper;
import com.woact.dolplads.exam2016.backend.testUtils.DeleterEJB;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
    @Inject
    private Logger logger;
    @EJB
    private UserEJB userEJB;
    @EJB
    private DeleterEJB deleterEJB;
    @EJB
    private PostEJB postEJB;
    @Inject
    private Validator validator;

    @After
    @Before
    public void setUp() throws Exception {
        deleterEJB.deleteEntities(Vote.class);
        deleterEJB.deleteEntities(Comment.class);
        deleterEJB.deleteEntities(Post.class);
        deleterEJB.deleteEntities(User.class);
        logger.log(Level.INFO, "preparing userservice test");
    }

    @Test
    public void testKarmaWithModeration() throws Exception {
        User user = getValidUser();
        user = userEJB.save(user);
        assertTrue(user != null);

        Post post = new Post(user, "someText");
        post = postEJB.createPost(post);
        assertTrue(post.getId() != null);

        Comment comment1 = new Comment(user, post, "comment1");
        Comment comment2 = new Comment(user, post, "comment2");
        Comment comment3 = new Comment(user, post, "comment3");
        Comment comment4 = new Comment(user, post, "comment4s");

        comment1 = postEJB.createComment(comment1);
        comment2 = postEJB.createComment(comment2);
        comment3 = postEJB.createComment(comment3);
        comment4 = postEJB.createComment(comment4);

        assertEquals(4, postEJB.getAllComments().size());
        assertEquals(1, postEJB.getAllPostByTime().size());

        Vote vote = postEJB.voteAgainst(user.getUserName(), post.getId());
        assertEquals(-1, postEJB.getCarmaPointsForUser(user.getUserName()));

        postEJB.voteForComment(user.getUserName(), comment1.getId(), 1);
        postEJB.voteForComment(user.getUserName(), comment2.getId(), 1);
        assertEquals(1, postEJB.getCarmaPointsForUser(user.getUserName()));


        postEJB.moderateComment(user.getUserName(), comment3.getId(), true);
        assertEquals(-9, postEJB.getCarmaPointsForUser(user.getUserName()));

        postEJB.moderateComment(user.getUserName(), comment4.getId(), true);
        assertEquals(-19, postEJB.getCarmaPointsForUser(user.getUserName()));

        User user2 = getValidUser();
        user2 = userEJB.save(user2);

        postEJB.voteForComment(user2.getUserName(), comment1.getId(),1);
        assertEquals(-18, postEJB.getCarmaPointsForUser(user.getUserName()));
    }

    /**
     * Tests not described in exam document
     *
     * @throws Exception
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