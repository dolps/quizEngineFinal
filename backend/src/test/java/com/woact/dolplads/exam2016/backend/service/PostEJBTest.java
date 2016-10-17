package com.woact.dolplads.exam2016.backend.service;

import com.woact.dolplads.exam2016.backend.entity.Comment;
import com.woact.dolplads.exam2016.backend.entity.Post;
import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.backend.entity.Vote;
import com.woact.dolplads.exam2016.backend.testUtils.ArquillianTestHelper;
import com.woact.dolplads.exam2016.backend.testUtils.DeleterEJB;
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
        deleterEJB.deleteEntities(Vote.class);
        deleterEJB.deleteEntities(Comment.class);
        deleterEJB.deleteEntities(Post.class);
        deleterEJB.deleteEntities(User.class);

        persistUser();
    }

    @Test
    public void createPost() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(post);

        assertNotNull(post.getId());
        assertEquals(post.getUser().getUserName(), testUser.getUserName());
    }

    @Test
    public void voteFor() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(post);


        postEJB.voteFor(testUser.getUserName(), post.getId());
        post = postEJB.findById(post.getId());

        assertEquals(1, post.getVotes().size());
        assertEquals(1, post.getVotes().get(0).getVoteValue());
    }

    @Test
    public void voteAgainst() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(post);

        postEJB.voteAgainst(testUser.getUserName(), post.getId());
        post = postEJB.findById(post.getId());

        assertEquals(1, post.getVotes().size());
        assertEquals(-1, post.getVotes().get(0).getVoteValue());
    }

    @Test
    public void cannotVoteForTwice() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(post);

        postEJB.voteFor(testUser.getUserName(), post.getId());
        postEJB.voteFor(testUser.getUserName(), post.getId());

        post = postEJB.findById(post.getId());

        assertEquals(1, post.getVotes().size());
    }

    @Test
    public void cannotVoteAgainstTwice() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(post);

        postEJB.voteAgainst(testUser.getUserName(), post.getId());
        postEJB.voteAgainst(testUser.getUserName(), post.getId());

        post = postEJB.findById(post.getId());

        assertEquals(1, post.getVotes().size());
    }

    @Test
    public void unVote() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(post);

        postEJB.voteFor(testUser.getUserName(), post.getId());
        post = postEJB.findById(post.getId());
        assertEquals(1, post.getVotes().size());

        postEJB.unVote(testUser.getUserName(), post.getId());
        post = postEJB.findById(post.getId());
        assertEquals(0, post.getVotes().size());
    }

    @Test
    public void testChangeVote() throws Exception {
        Post post = getValidPost();
        post = postEJB.createPost(post);

        postEJB.voteFor(testUser.getUserName(), post.getId());
        post = postEJB.findById(post.getId());
        assertEquals(1, post.getVotes().get(0).getVoteValue());

        postEJB.voteAgainst(testUser.getUserName(), post.getId());
        post = postEJB.findById(post.getId());
        assertEquals(-1, post.getVotes().get(0).getVoteValue());

        postEJB.unVote(testUser.getUserName(), post.getId());
        post = postEJB.findById(post.getId());
        assertEquals(0, post.getVotes().size());
    }

    @Test
    public void getAllPostByTime() throws Exception {
        Post post1 = getValidPost();
        Post post2 = getValidPost();

        postEJB.createPost(post1);
        Thread.sleep(1000); // 1 second delay should be enough noticed that 1/2 second could give trouble
        postEJB.createPost(post2);

        post1 = postEJB.findById(post1.getId());
        post2 = postEJB.findById(post2.getId());

        // should reveal if transactions have been done in wrong order
        assertTrue(post1.getCreationDate().before(post2.getCreationDate()));

        List<Post> posts = postEJB.getAllPostByTime();

        assertTrue(posts.get(0).getCreationDate()
                .compareTo(posts.get(1).getCreationDate()) > 0);

        Post latestAdded = posts.get(0);
        assertEquals(post2.getId(), latestAdded.getId());
    }

    @Test
    public void getAllPostsByScore() throws Exception {
        Post post1 = getValidPost();
        Post post2 = getValidPost();

        postEJB.createPost(post1);
        postEJB.createPost(post2);

        postEJB.voteFor(testUser.getUserName(), post1.getId());
        postEJB.voteAgainst(testUser.getUserName(), post2.getId());
        List<Post> posts = postEJB.getAllPostsByScore();

        assertEquals(post1.getId(), posts.get(0).getId());

        postEJB.voteFor(testUser.getUserName(), post2.getId());
        postEJB.voteAgainst(testUser.getUserName(), post1.getId());
        posts = postEJB.getAllPostsByScore();

        assertEquals(post2.getId(), posts.get(0).getId());
    }

    @Test // TODO: 17/10/2016 maybe add back to back ref?
    public void createComment() throws Exception {
        Post post1 = getValidPost();
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
        Post post = getValidPost();
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
        Post post = getValidPost();
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
        Post post = getValidPost();
        postEJB.createPost(post);
        Comment comment = new Comment(testUser, post, "comment");
        comment = postEJB.createComment(comment);

        postEJB.voteForComment(testUser.getUserName(), comment.getId(), 1);

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