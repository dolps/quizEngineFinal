package com.woact.dolplads.exam2016.backend.repository;

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
 * Created by dolplads on 12/10/2016.
 * <p>
 * This test just test that Crud operations work
 * as supposed to with valid inputs
 */
public class UserRepositoryTest extends ArquillianTestHelper {
    @EJB
    private DeleterEJB deleterEJB;
    @EJB
    private UserRepository userRepository;
    @Inject
    private Logger logger;

    @Before
    @After
    public void restoreDB() throws Exception {
        logger.log(Level.INFO, "restoring DB");
        deleterEJB.deleteEntities(User.class);
    }

    @Test
    public void save() throws Exception {
        int size = userRepository.findAll().size();

        User user = getValidUser();
        user = userRepository.save(user);

        assertEquals(size + 1, userRepository.findAll().size());
    }

    @Test
    public void findById() throws Exception {
        User user = getValidUser();
        user = userRepository.save(user);

        assertNotNull(userRepository.findById(user.getUserName()));
        assertNull(userRepository.findById("nonExisting"));
    }

    @Test
    public void remove() throws Exception {
        int size = userRepository.findAll().size();

        User user = getValidUser();
        user = userRepository.save(user);

        assertEquals(size + 1, userRepository.findAll().size());

        userRepository.remove(user);

        assertNull(userRepository.findById(user.getUserName()));
        assertEquals(size, userRepository.findAll().size());
    }

    @Test
    public void update() throws Exception {
        User user = getValidUser();
        user.setUserName("thomas");
        user = userRepository.save(user);

        user = userRepository.findById(user.getUserName());
        assertEquals("thomas", user.getUserName());

        user.setUserName("newName");
        user = userRepository.update(user);

        user = userRepository.findById(user.getUserName());
        assertEquals("newName", user.getUserName());
    }

    @Test
    public void findAll() throws Exception {
        User user1 = getValidUser();
        user1.setUserName("unique1");
        User user2 = getValidUser();
        user2.setUserName("unique2");

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        assertEquals(2, users.size());
    }

    public User getValidUser() {
        User u = new User("userName", "user", "us", "userson");
        u.setPasswordHash("hashedpassword");
        u.setSalt("aSalts");

        return u;
    }
}