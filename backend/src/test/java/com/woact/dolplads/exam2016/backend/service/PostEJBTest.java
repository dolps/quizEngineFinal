package com.woact.dolplads.exam2016.backend.service;

import com.woact.dolplads.exam2016.backend.entity.*;
import com.woact.dolplads.exam2016.backend.testUtils.ArquillianTestHelper;
import com.woact.dolplads.exam2016.backend.testUtils.DeleterEJB;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJB;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 17/10/2016.
 */
@Log
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

        log.log(Level.INFO, "prepared DB");
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

        postEJB.unVotePost(testUser.getUserName(), post.getId());
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

        log.log(Level.INFO, "p2 has id: " + p2.getId());
        log.log(Level.INFO, "p1 has id: " + p1.getId());

        postEJB.voteForPost(testUser.getUserName(), p1.getId(), 1);
        postEJB.voteForPost(testUser.getUserName(), p2.getId(), -1);

        List<Post> posts = postEJB.findAllPostsByScore();
        assertEquals(p1.getId(), posts.get(0).getId());

        postEJB.voteForPost(testUser.getUserName(), p1.getId(), -1);
        postEJB.voteForPost(testUser.getUserName(), p2.getId(), 1);

        assertEquals(-1, postEJB.findPost(p1.getId()).getScore());
        assertEquals(1, postEJB.findPost(p2.getId()).getScore());

        log.log(Level.INFO, "p2 still has id: " + p2.getId());
        log.log(Level.INFO, "p1 still has id: " + p1.getId());

        posts = postEJB.findAllPostsByScore();
        log.log(Level.INFO, "postScores" + posts.get(0).getId() + " val " + posts.get(0).getScore());
        log.log(Level.INFO, "postScores" + posts.get(1).getId() + " val " + posts.get(1).getScore());
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
/*
    @Test
    public void voteFor() throws Exception {
        AbstractPost post = getValidPost();
        post = postEJB.createPost(post);


        postEJB.votePost(testUser.getUserName(), post.getId(), 1);
        post = postEJB.findById(post.getId());

        //assertEquals(1, post.getVotes().size());
        //assertEquals(1, post.getVotes().get(0).getVoteValue());
    }

    @Test
    public void voteAgainst() throws Exception {
        AbstractPost post = getValidPost();
        post = postEJB.createPost(post);

        postEJB.votePost(testUser.getUserName(), post.getId(), -1);
        post = postEJB.findById(post.getId());

        //assertEquals(1, post.getVotes().size());
        //assertEquals(-1, post.getVotes().get(0).getVoteValue());
    }

    @Test
    public void cannotVoteForTwice() throws Exception {
        AbstractPost post = getValidPost();
        post = postEJB.createPost(post);

        postEJB.votePost(testUser.getUserName(), post.getId(), 1);
        postEJB.votePost(testUser.getUserName(), post.getId(), 1);

        post = postEJB.findById(post.getId());

        //assertEquals(1, post.getVotes().size());
    }

    @Test
    public void cannotVoteAgainstTwice() throws Exception {
        AbstractPost post = getValidPost();
        post = postEJB.createPost(post);

        postEJB.votePost(testUser.getUserName(), post.getId(), -1);
        postEJB.votePost(testUser.getUserName(), post.getId(), -1);

        post = postEJB.findById(post.getId());

        //assertEquals(1, post.getVotes().size());
    }

    @Test
    public void unVote() throws Exception {
        AbstractPost post = getValidPost();
        post = postEJB.createPost(post);

        postEJB.votePost(testUser.getUserName(), post.getId(), 1);
        post = postEJB.findById(post.getId());
        //assertEquals(1, post.getVotes().size());

        //postEJB.unVotePost(testUser.getUserName(), post.getId());
        post = postEJB.findById(post.getId());
        //assertEquals(0, post.getVotes().size());
    }

    @Test
    public void testChangeVote() throws Exception {
        AbstractPost post = getValidPost();
        post = postEJB.createPost(post);

        postEJB.votePost(testUser.getUserName(), post.getId(), 1);
        post = postEJB.findById(post.getId());
        //assertEquals(1, post.getVotes().get(0).getVoteValue());

        postEJB.votePost(testUser.getUserName(), post.getId(), -1);
        post = postEJB.findById(post.getId());
        //assertEquals(-1, post.getVotes().get(0).getVoteValue());

        //postEJB.unVotePost(testUser.getUserName(), post.getId());
        post = postEJB.findById(post.getId());
        //assertEquals(0, post.getVotes().size());
    }

    @Test
    public void getAllPostByTime() throws Exception {
        AbstractPost post1 = getValidPost();
        AbstractPost post2 = getValidPost();

        postEJB.createPost(post1);
        Thread.sleep(1000); // 1 second delay should be enough noticed that 1/2 second could give trouble
        postEJB.createPost(post2);

        post1 = postEJB.findById(post1.getId());
        post2 = postEJB.findById(post2.getId());

        // should reveal if transactions have been done in wrong order
        assertTrue(post1.getCreationDate().before(post2.getCreationDate()));

        List<AbstractPost> posts = postEJB.getAllPostByTime();

        assertTrue(posts.get(0).getCreationDate()
                .compareTo(posts.get(1).getCreationDate()) > 0);

        AbstractPost latestAdded = posts.get(0);
        assertEquals(post2.getId(), latestAdded.getId());
    }

    @Test
    public void getAllPostsByScore() throws Exception {
        AbstractPost post1 = getValidPost();
        AbstractPost post2 = getValidPost();

        post1 = postEJB.createPost(post1);
        post2 = postEJB.createPost(post2);

        postEJB.votePost(testUser.getUserName(), post1.getId(), 10); // post1 score = 1
        post1 = postEJB.findById(post1.getId());
        assertEquals(1, post1.getScore());

        postEJB.votePost(testUser.getUserName(), post2.getId(), -1); // post2 score = -1
        post1 = postEJB.findById(post2.getId());
        assertEquals(-1, post2.getScore());

        List<AbstractPost> posts = postEJB.getAllPostsByScore();
        assertEquals(post1.getId(), posts.get(0).getId());

        postEJB.votePost(testUser.getUserName(), post1.getId(), -1); // post1 score = -1
        postEJB.votePost(testUser.getUserName(), post2.getId(), 1); // post2 score = 1
        posts = postEJB.getAllPostsByScore();

        assertEquals(post2.getId(), posts.get(0).getId());
    }

    @Test // TODO: 17/10/2016 maybe add back to back ref?
    public void createComment() throws Exception {
        AbstractPost post1 = getValidPost();
        postEJB.createPost(post1);

        Comment comment = new Comment(testUser, post1, "comment");
        comment.setModerated(true);
        postEJB.createComment(comment);
        assertEquals(1, postEJB.getAllComments().size());
        assertEquals(1, postEJB.getAllPostByTime().size());
        assertNotNull(comment.getId());

        List<Comment> comments = postEJB.findCommentsByPost(post1.getId());
        assertEquals(1, comments.size());
        assertEquals(post1.getId(), comments.get(0).getPost().getId());
        assertEquals(comments.get(0).isModerated(), true);
    }

    @Test
    public void moderateOwn() throws Exception {
        AbstractPost post = getValidPost();
        postEJB.createPost(post);

        Comment comment = new Comment(testUser, post, "comment");
        comment = postEJB.createComment(comment);
        assertFalse(comment.isModerated());

        postEJB.moderateComment(testUser.getUserName(), comment.getId(), true);
        comment = postEJB.findCommentById(comment.getId());
        assertTrue(comment.isModerated());
    }

    @Test
    public void failModerateOther() throws Exception {
        AbstractPost post = getValidPost();
        postEJB.createPost(post);
        Comment comment = new Comment(testUser, post, "comment");
        comment = postEJB.createComment(comment);
        assertFalse(comment.isModerated());

        persistUser();

        postEJB.moderateComment(testUser.getUserName(), comment.getId(), true);
        comment = postEJB.findCommentById(comment.getId());
        assertFalse(comment.isModerated());
    }

    @Test
    public void voteForComment() throws Exception {
        AbstractPost post = getValidPost();
        postEJB.createPost(post);
        Comment comment = new Comment(testUser, post, "comment");
        comment = postEJB.createComment(comment);

        postEJB.voteComment(testUser.getUserName(), comment.getId(), 1);

    }
    */

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