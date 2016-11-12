package com.woact.dolplads.quiz.rest.backend.service;

import com.woact.dolplads.quiz.rest.backend.entity.Post;
import com.woact.dolplads.quiz.rest.backend.entity.Vote;
import com.woact.dolplads.quiz.rest.backend.testUtils.ArquillianTestHelper;
import com.woact.dolplads.quiz.rest.backend.testUtils.DeleterEJB;
import com.woact.dolplads.quiz.rest.backend.entity.Comment;
import com.woact.dolplads.quiz.rest.backend.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJB;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 17/10/2016.
 */
public class PostEJBTest extends ArquillianTestHelper {
    private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis());
    @EJB
    private DeleterEJB deleterEJB;
    @EJB
    private UserEJB userEJB;
    @EJB
    private PostEJB postEJB;

    private User testUser;

    @Before
    @After
    public void setUp() throws Exception {
        deleterEJB.deleteEntities(Post.class);
        deleterEJB.deleteEntities(Comment.class);
        deleterEJB.deleteEntities(Vote.class);
        deleterEJB.deleteEntities(User.class);

        persistUser();
    }

    @Test
    public void createPost() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(testUser.getUserName(), post);

        assertNotNull(post.getId());
        assertEquals(post.getUser().getUserName(), testUser.getUserName());
    }

    @Test
    public void testVoteFor() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(testUser.getUserName(), post);
        postEJB.voteForPost(testUser.getUserName(), post.getId(), 1);

        post = postEJB.findPost(post.getId());

        assertEquals(1, post.getScore());

        testUser = userEJB.findById(testUser.getUserName());
    }

    @Test
    public void testVoteAgainst() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(testUser.getUserName(), post);
        postEJB.voteForPost(testUser.getUserName(), post.getId(), -1);

        post = postEJB.findPost(post.getId());
        assertEquals(-1, post.getScore());
    }

    @Test
    public void testCannotVoteForTwice() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(testUser.getUserName(), post);

        postEJB.voteForPost(testUser.getUserName(), post.getId(), 1);
        postEJB.voteForPost(testUser.getUserName(), post.getId(), 1);

        post = postEJB.findPost(post.getId());
        assertEquals(1, post.getScore());
    }

    @Test
    public void testCannotVoteAgainstTwice() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(testUser.getUserName(), post);

        postEJB.voteForPost(testUser.getUserName(), post.getId(), -1);
        postEJB.voteForPost(testUser.getUserName(), post.getId(), -1);

        post = postEJB.findPost(post.getId());
        assertEquals(-1, post.getScore());
    }

    @Test
    public void testUnvote() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(testUser.getUserName(), post);

        postEJB.voteForPost(testUser.getUserName(), post.getId(), 1);
        assertEquals(1, postEJB.findPost(post.getId()).getScore());

        postEJB.voteForPost(testUser.getUserName(), post.getId(), 0);
        assertEquals(0, postEJB.findPost(post.getId()).getScore());
    }

    @Test
    public void testChangeVote() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(testUser.getUserName(), post);

        postEJB.voteForPost(testUser.getUserName(), post.getId(), -1);
        assertEquals(-1, postEJB.findPost(post.getId()).getScore());

        postEJB.voteForPost(testUser.getUserName(), post.getId(), 1);
        assertEquals(1, postEJB.findPost(post.getId()).getScore());
    }

    @Test
    public void testGetAllPostByTime() throws Exception {
        Post p1 = getValidPost();
        Post p2 = getValidPost();

        postEJB.createPost(testUser.getUserName(), p1);
        Thread.sleep(1_000);
        postEJB.createPost(testUser.getUserName(), p2);
        p1 = postEJB.findPost(p1.getId());
        p2 = postEJB.findPost(p2.getId());

        assertTrue(p1.getCreationDate().before(p2.getCreationDate()));

        List<Post> posts = postEJB.findAllPostsByTime();

        assertEquals(p2.getId(), posts.get(0).getId());
    }

    @Test
    public void testGetAllPostsByScore() throws Exception {
        Post p1 = getValidPost();
        Post p2 = getValidPost();

        postEJB.createPost(testUser.getUserName(), p1);
        postEJB.createPost(testUser.getUserName(), p2);

        postEJB.voteForPost(testUser.getUserName(), p1.getId(), 1);
        postEJB.voteForPost(testUser.getUserName(), p2.getId(), -1);

        List<Post> posts = postEJB.findAllPostsByScore();
        assertEquals(p1.getId(), posts.get(0).getId());

        postEJB.voteForPost(testUser.getUserName(), p1.getId(), -1);
        postEJB.voteForPost(testUser.getUserName(), p2.getId(), 1);

        assertEquals(-1, postEJB.findPost(p1.getId()).getScore());
        assertEquals(1, postEJB.findPost(p2.getId()).getScore());

        posts = postEJB.findAllPostsByScore();
        assertEquals(p2.getId(), posts.get(0).getId());
    }

    @Test
    public void testWithMultipleUsers() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(testUser.getUserName(), post);
        postEJB.voteForPost(testUser.getUserName(), post.getId(), 1);
        persistUser();
        postEJB.voteForPost(testUser.getUserName(), post.getId(), 1);

        post = postEJB.findPost(post.getId());
        assertEquals(2, post.getScore());
    }

    @Test
    public void testCreateComment() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(testUser.getUserName(), post);
        Comment comment = new Comment(testUser, "someText");
        Comment comment2 = new Comment(testUser, "someText");

        postEJB.createCommentForPost(post.getId(), comment);
        assertNotNull(comment.getId());

        postEJB.createCommentForPost(post.getId(), comment2);
        assertNotNull(comment2.getId());

        assertEquals(2, postEJB.findAllComments().size());
    }

    @Test
    public void testModerateOwn() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(testUser.getUserName(), post);
        Comment comment = new Comment(testUser, "someText");
        comment = postEJB.createCommentForPost(post.getId(), comment);

        postEJB.moderateComment(testUser.getUserName(), comment.getId(), true);

        comment = postEJB.findComment(comment.getId());

        assertTrue(comment.isModerated());
    }

    @Test
    public void testFailModerateOther() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(testUser.getUserName(), post);
        Comment comment = new Comment(testUser, "someText");
        comment = postEJB.createCommentForPost(post.getId(), comment);

        persistUser();

        postEJB.moderateComment(testUser.getUserName(), comment.getId(), true);
        comment = postEJB.findComment(comment.getId());
        assertFalse(comment.isModerated());
    }

    @Test
    public void testVoteForComment() {
        Post post = getValidPost();
        post = postEJB.createPost(testUser.getUserName(), post);
        Comment comment = new Comment(testUser, "someText");
        comment = postEJB.createCommentForPost(post.getId(), comment);

        postEJB.voteForComment(testUser.getUserName(), comment.getId(), 1);

        assertEquals(1, postEJB.findComment(comment.getId()).getScore());
    }

    private void persistUser() {
        User user = new User("userName" + counter.incrementAndGet(), "testUser", "us", "userson");
        user.setPasswordHash("password");
        user.setSalt("aSalt");
        this.testUser = userEJB.save(user);

        assertNotNull(testUser);
    }

    private Post getValidPost() {
        return new Post(this.testUser, "text");
    }

}