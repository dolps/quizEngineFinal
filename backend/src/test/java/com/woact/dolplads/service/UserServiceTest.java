package com.woact.dolplads.service;

import com.woact.dolplads.entity.Address;
import com.woact.dolplads.entity.User;
import com.woact.dolplads.enums.CountryEnum;
import com.woact.dolplads.injector.DolpLogger;
import com.woact.dolplads.testUtils.ArquillianTestHelper;
import com.woact.dolplads.testUtils.DeleterEJB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJB;
import javax.inject.Inject;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 12/10/2016.
 */
public class UserServiceTest extends ArquillianTestHelper {

    @DolpLogger
    private EmptyClass emptyClass;


    @Inject
    private Logger logger;
    @EJB
    private UserService userService;
    @EJB
    private DeleterEJB deleterEJB;


    @After
    @Before
    public void setUp() throws Exception {
        deleterEJB.deleteEntities(User.class);
        logger.log(Level.INFO, "preparing userservice test");
    }

    @Test
    public void save() throws Exception {

        User user = getValidUser();
        user.setUserName("userName");
        user.setPasswordHash("passwordHash");

        boolean created = userService.save(user).getId() != null;

        assertTrue(created);
    }

    @Test
    public void login() throws Exception {
        User user = getValidUser();
        user.setUserName("userName");
        user.setPasswordHash("passwordHash");

        assertTrue(userService.save(user).getId() != null);

        userService.login("userName", "passwordHash");
    }


    private static User getValidUser() {
        return new User("thomas", "dolplads", "userName", new Address("street", "1342", CountryEnum.Norway));
    }


}