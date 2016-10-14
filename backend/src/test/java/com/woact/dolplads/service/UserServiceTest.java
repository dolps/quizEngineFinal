package com.woact.dolplads.service;

import com.woact.dolplads.entity.Address;
import com.woact.dolplads.entity.User;
import com.woact.dolplads.enums.CountryEnum;
import com.woact.dolplads.injector.DolpLogger;
import com.woact.dolplads.testUtils.ArquillianTestHelper;
import com.woact.dolplads.testUtils.DeleterEJB;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 12/10/2016.
 */
public class UserServiceTest extends ArquillianTestHelper {
    @Inject
    private Logger logger;
    @EJB
    private UserService userService;
    @EJB
    private DeleterEJB deleterEJB;
    @Inject
    private Validator validator;

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

    /**
     * Tests that the address has a valid country
     * also tests that the validation works through the user
     *
     * @throws Exception
     */
    @Test
    public void testCreateUserWithWrongCountry() throws Exception {
        User user = getValidUser();
        user.getAddress().setCountryEnum(CountryEnum.England);

        assertEquals(1, validator.validateProperty(user.getAddress(), "countryEnum").size());
        assertEquals(1, validator.validate(user).size());
    }

    @Test
    public void login() throws Exception {
        User user = getValidUser();
        user.setUserName("userName");
        user.setPasswordHash("passwordHash");

        assertTrue(userService.save(user).getId() != null);

        userService.login("userName", "passwordHash");
    }


    private User getValidUser() {
        User u = new User("thomas", "dolplads", "userName", new Address("street", "1342", CountryEnum.Norway));
        u.setPasswordHash("hash");
        u.setSalt("salt");
        assertEquals(0, validator.validate(u).size());

        return u;
    }


}