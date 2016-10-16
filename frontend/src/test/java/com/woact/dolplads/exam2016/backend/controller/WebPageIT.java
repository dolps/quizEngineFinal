package com.woact.dolplads.controller;

import com.woact.dolplads.entity.User;
import com.woact.dolplads.enums.CountryEnum;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pages.CreateUserPageObject;
import pages.HomePageObject;
import pages.LoginPageObject;
import testUtils.SeleniumTestBase;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 01/10/16.
 */
@Log
public class WebPageIT extends SeleniumTestBase {
    private HomePageObject homePageObject;
    private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis());

    @Before
    public void setUp() throws Exception {
        homePageObject = new HomePageObject(getDriver());
        homePageObject.toStartPage();

        assertTrue(homePageObject.isOnPage());
    }

    /**
     * we want to start a fresh session for each method
     * since unit tests are supposed to be test of units
     * eg. if user is logged in from last test, that should not
     * effect next test
     */
    @After
    public void destroySession() {
        getDriver().manage().deleteAllCookies();
        int cookies = getDriver().manage().getCookies().size();
        assertEquals(0, cookies);
    }

    @Test
    public void testIsHome() throws Exception {
        assertTrue(homePageObject.isOnPage());
    }


    /**
     * Test that user is redirected to Login on clicking login button link
     *
     * @throws Exception
     */
    @Test
    public void testLoginLink() throws Exception {
        LoginPageObject loginPageObject = homePageObject.toLogin();

        assertTrue(loginPageObject.isOnPage());
    }

    /**
     * Test that login does not work when giving the wrong credentials
     *
     * @throws Exception
     */
    @Test
    public void testLoginWrongUser() throws Exception {
        LoginPageObject loginPageObject = homePageObject.toLogin();

        assertTrue(loginPageObject.isOnPage());

        String wrongCredentials = "test";

        loginPageObject.login(wrongCredentials, wrongCredentials);

        assertTrue(loginPageObject.isOnPage());
    }

    @Test
    public void testCreateUserFailDueToPasswordMismatch() throws Exception {
        LoginPageObject loginPageObject = homePageObject.toLogin();
        assertTrue(loginPageObject.isOnPage());

        CreateUserPageObject createUserPage = loginPageObject.toCreate();
        assertTrue(createUserPage.isOnPage());

        createUserPage
                .createUser("userName" + getUniqueId(), "password", "confirmPassword", "firstName", "middleName", "lastName", CountryEnum.Norway);

        assertTrue(createUserPage.isOnPage());
    }

    @Test
    public void testCreateValidUser() throws Exception {
        LoginPageObject loginPageObject = homePageObject.toLogin();
        assertTrue(loginPageObject.isOnPage());

        CreateUserPageObject createUserPageObject = loginPageObject.toCreate();
        assertTrue(createUserPageObject.isOnPage());
        String unique = getUniqueId();
        createUserPageObject.createUser(unique, "password", "password", "first", "second", "last", CountryEnum.Norway);
        assertTrue(homePageObject.isOnPage());
        assertTrue(homePageObject.isLoggedIn(unique));
    }

    @Test
    public void testLogin() throws Exception {
        // save, login and assert
        User u = createUniqueUserThenLogIn();
        assertTrue(homePageObject.isOnPage());
        assertTrue(homePageObject.isLoggedIn(u.getUserName()));

        // logout and assert
        homePageObject = homePageObject.logOut();
        assertTrue(homePageObject.isOnPage());
        assertTrue(homePageObject.isLoggedOut());

        // login and assert
        LoginPageObject loginPageObject = homePageObject.toLogin();
        assertTrue(loginPageObject.isOnPage());

        homePageObject = loginPageObject.login(u.getUserName(), u.getPasswordHash());
        assertTrue(homePageObject.isOnPage());
        assertTrue(homePageObject.isLoggedIn(u.getUserName()));
    }

    private User createUniqueUserThenLogIn() throws InterruptedException {
        LoginPageObject loginPageObject = homePageObject.toLogin();
        assertTrue(loginPageObject.isOnPage());

        CreateUserPageObject createUserPageObject = loginPageObject.toCreate();
        assertTrue(createUserPageObject.isOnPage());

        String unique = getUniqueId();

        return createUserPageObject
                .createUser(unique, "password", "password", "first", "second", "last", CountryEnum.Norway);
    }

    private void loginExistingUser(User user) {
        LoginPageObject loginPageObject = homePageObject.toLogin();
        loginPageObject.login(user.getUserName(), user.getPasswordHash());
    }


    private String getUniqueId() {
        return "foo" + counter.incrementAndGet();
    }

    protected static String getUniqueTitle() {
        return "A title: " + counter.incrementAndGet();
    }


}