package com.woact.dolplads.exam2016.frontend;

import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.frontend.pages.*;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.woact.dolplads.exam2016.frontend.testUtils.SeleniumTestBase;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 01/10/16.
 */
@Ignore
@Log
public class MyNewsIT extends SeleniumTestBase {
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
    public void testCreateNews() throws InterruptedException {
        LoginPageObject loginPageObject = homePageObject.toLogin();
        CreateUserPageObject createUserPage = loginPageObject.toCreate();
        User u = createUniqueUserThenLogIn();
        assertTrue(homePageObject.isOnPage());
        assertTrue(homePageObject.isLoggedIn(u.getUserName()));

        assertEquals(0, homePageObject.getNumberOfNews(u.getUserName()));
        homePageObject.createNews("someText");
        assertEquals(1, homePageObject.getNumberOfNews(u.getUserName()));

        homePageObject.createNews("someText");
        assertEquals(2, homePageObject.getNumberOfNews(u.getUserName()));


        Thread.sleep(2000);
    }

    @Test
    public void testNewsAfterLogout() throws Exception {
        LoginPageObject loginPageObject = homePageObject.toLogin();
        User u = createUniqueUserThenLogIn();
        homePageObject.createNews("someText1");
        homePageObject.createNews("someText2");
        assertEquals(2, homePageObject.getNumberOfNews(u.getUserName()));
        homePageObject.logOut();
        assertEquals(2, homePageObject.getNumberOfNews(u.getUserName()));

        Thread.sleep(2000);
    }

    @Test
    public void testUserDetails() throws Exception {
        User u = createUniqueUserThenLogIn();
        homePageObject.createNews("testtext");
        UserDetailsPageObject userDetails = homePageObject.toUserDetails(u.getUserName());

        Thread.sleep(5000);
        assertTrue(userDetails.isOnPage());
    }

    @Test
    public void testCanVote() throws Exception {
        User u = createUniqueUserThenLogIn();
        homePageObject.createNews("toastgoodnow");

        boolean voteWorked = homePageObject.voteForNews(u.getUserName());
        assertTrue(voteWorked);

        homePageObject.logOut();
        voteWorked = homePageObject.voteForNews(u.getUserName());
        assertFalse(voteWorked);

        loginExistingUser(u);
        voteWorked = homePageObject.voteForNews(u.getUserName());
        assertTrue(voteWorked);
    }

    /**
     * sort posts by time
     * ◦ assert that the most recent post has a 0 score ◦ upvote the post
     * ◦ verify that post has now score +1 ◦ downvote the post
     * ◦ verify that post has now score -1 ◦ unvote the post
     * ◦ verify that post has now score 0
     *
     * @throws Exception
     */
    @Test
    public void testScore() throws Exception {
        User u = createUniqueUserThenLogIn();
        homePageObject.createNews("coffecoffecoffe");
        homePageObject.sortNewsBy("time");
        Thread.sleep(3000);
        assertEquals(0, homePageObject.getScoreForFirstPost());

        homePageObject.voteForFirstPost(1);
        assertEquals(1, homePageObject.getScoreForFirstPost());

        homePageObject.voteForFirstPost(-1);
        assertEquals(-1, homePageObject.getScoreForFirstPost());
        Thread.sleep(2000);
        homePageObject.voteForFirstPost(0);
        assertEquals(0, homePageObject.getScoreForFirstPost());

        //homePageObject.sortNewsBy("score");
        Thread.sleep(2000);
        //new Select(driver.findElement(By.id("createUserForm:country"))).selectByVisibleText(country);
    }

    @Test
    public void testScoreWithTwoUsers() throws Exception {
        User u1 = createUniqueUserThenLogIn();
        homePageObject.createNews("juuuhuu");
        homePageObject.sortNewsBy("time");
        homePageObject.voteForFirstPost(1);

        assertEquals(1, homePageObject.getScoreForFirstPost());

        homePageObject.logOut();
        User u2 = createUniqueUserThenLogIn();
        homePageObject.voteForFirstPost(1);
        assertEquals(2, homePageObject.getScoreForFirstPost());
    }

    @Test
    public void testLongText() throws Exception {
        User u1 = createUniqueUserThenLogIn();
        String text = "thisisaverylongtexttsdfsdfsdfsdfhatdontfitindinsidethetextboxthatwell";
        homePageObject.createNews(text);
        homePageObject.sortNewsBy("time");

        boolean trimmed = homePageObject.checkifTrimmed();
        assertTrue(trimmed);
    }

    @Test
    public void testSorting() throws Exception {
        User u1 = createUniqueUserThenLogIn();
        homePageObject.createNews("sometext");
        homePageObject.sortNewsBy("time");

        homePageObject.voteForFirstPost(1);
        homePageObject.createNews("otherpost");
        // verify not sorted by score
        boolean sorted = homePageObject.isSortedByScore();
        assertFalse(sorted);
        // sort by score
        homePageObject.sortNewsBy("score");
        sorted = homePageObject.isSortedByScore();
        assertTrue(sorted);

        homePageObject.sortNewsBy("time");
        sorted = homePageObject.isSortedByScore();
        assertFalse(sorted);
    }

    @Test
    public void tesCreateComment() throws Exception {
        User u1 = createUniqueUserThenLogIn();
        homePageObject.createNews("old news");
        NewsDetailsPageObject newsDetailsPage = homePageObject.toNewsDetails(u1.getUserName());
        assertTrue(newsDetailsPage.isOnPage());

        int n = newsDetailsPage.getNumberOfCommentsByUser(u1.getUserName());
        assertTrue(n == 0);

        newsDetailsPage.createComment(u1.getUserName());
        newsDetailsPage.createComment(u1.getUserName());
        newsDetailsPage.createComment(u1.getUserName());

        n = newsDetailsPage.getNumberOfCommentsByUser(u1.getUserName());
        assertTrue(n == 3);
    }

    @Test
    public void canModerate() throws Exception {
        User u1 = createUniqueUserThenLogIn();
        homePageObject.createNews("news");
        NewsDetailsPageObject newsDetailsPage = homePageObject.toNewsDetails(u1.getUserName());
        assertTrue(newsDetailsPage.isOnPage());

        newsDetailsPage.createComment("this is comment");

        boolean moderated = newsDetailsPage.moderateComment(u1.getUserName());
        assertTrue(moderated);

        // just getting the post back to origin state for next asserts
        moderated = newsDetailsPage.moderateComment(u1.getUserName());
        assertFalse(moderated);

        homePageObject = newsDetailsPage.logOut();
        assertTrue(homePageObject.isOnPage());

        newsDetailsPage = homePageObject.toNewsDetails(u1.getUserName());
        moderated = newsDetailsPage.moderateComment(u1.getUserName());
        assertFalse(moderated);

        homePageObject = newsDetailsPage.goHome();
        createUniqueUserThenLogIn();
        newsDetailsPage = homePageObject.toNewsDetails(u1.getUserName());
        moderated = newsDetailsPage.moderateComment(u1.getUserName());
        assertFalse(moderated);

        homePageObject = newsDetailsPage.logOut();
        loginExistingUser(u1);
        newsDetailsPage = homePageObject.toNewsDetails(u1.getUserName());
        moderated = newsDetailsPage.moderateComment(u1.getUserName());
        assertTrue(moderated);
    }

    // old tests
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

        //   createUserPage
        //.createUser("userName" + getUniqueId(), "password", "confirmPassword", "firstName", "middleName", "lastName", CountryEnum.Norway);

        assertTrue(createUserPage.isOnPage());
    }

    @Test
    public void testCreateValidUser() throws Exception {
        LoginPageObject loginPageObject = homePageObject.toLogin();
        assertTrue(loginPageObject.isOnPage());

        CreateUserPageObject createUserPageObject = loginPageObject.toCreate();
        assertTrue(createUserPageObject.isOnPage());
        String unique = getUniqueId();
        //createUserPageObject.createUser(unique, "password", "password", "first", "second", "last", CountryEnum.Norway);
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
                .createUser(unique, "pass", "pass", "usur", "user", "usersson");
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