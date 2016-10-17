package com.woact.dolplads.exam2016.backend.repository;

import com.woact.dolplads.exam2016.backend.entity.Post;
import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.backend.testUtils.ArquillianTestHelper;
import com.woact.dolplads.exam2016.backend.testUtils.DeleterEJB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJB;
import javax.inject.Inject;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 17/10/2016.
 */
public class PostRepositoryTest extends ArquillianTestHelper {
    @EJB
    private DeleterEJB deleterEJB;
    @EJB
    private PostRepository postRepository;
    @EJB
    private UserRepository userRepository;
    @Inject
    private Logger logger;
    private User user;

    @Before
    @After
    public void prepareDB() throws Exception {
        logger.log(Level.INFO, "restoring DB");
        deleterEJB.deleteEntities(Post.class);
        deleterEJB.deleteEntities(User.class);

        persistUser();
    }

    @Test
    public void save() throws Exception {
        int size = postRepository.findAll().size();

        Post post = getValidPost();
        post = postRepository.save(post);

        assertEquals(size + 1, postRepository.findAll().size());
        assertNotNull(postRepository.findById(post.getId()));
    }

    @Test
    public void findById() throws Exception {
        Post post = getValidPost();
        post = postRepository.save(post);

        assertNotNull(postRepository.findById(post.getId()));
        assertNull(postRepository.findById(10L));
    }

    @Test
    public void remove() throws Exception {
        int size = postRepository.findAll().size();
        Post post = getValidPost();
        post = postRepository.save(post);

        postRepository.remove(post);
        assertNull(postRepository.findById(post.getId()));
        assertEquals(size, postRepository.findAll().size());
    }

    @Test
    public void update() throws Exception {
        Post post = getValidPost();
        post.setText("before");
        post = postRepository.save(post);

        post = postRepository.findById(post.getId());
        assertEquals("before", post.getText());

        post.setText("after");
        post = postRepository.update(post);

        post = postRepository.findById(post.getId());
        assertEquals("after", post.getText());
    }

    @Test
    public void findAll() throws Exception {
        Post post = getValidPost();
        Post post2 = getValidPost();

        postRepository.save(post);
        postRepository.save(post2);

        List<Post> posts = postRepository.findAll();

        assertEquals(2, posts.size());
    }

    private void persistUser() {
        User user = new User("userName", "user", "us", "userson");
        user.setPasswordHash("password");
        user.setSalt("aSalt");
        this.user = userRepository.save(user);
    }

    private Post getValidPost() {
        return new Post(this.user, "text");
    }
}