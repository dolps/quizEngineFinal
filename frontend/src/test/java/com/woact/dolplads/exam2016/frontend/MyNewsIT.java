package com.woact.dolplads.exam2016.frontend;

import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.frontend.pages.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.woact.dolplads.exam2016.frontend.testUtils.SeleniumTestBase;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 01/10/16.
 * Main Integration tests
 */
public class MyNewsIT extends SeleniumTestBase {
    private HomePageObject homePageObject;
    private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis());

    @Before
    public void setUp() throws Exception {
        homePageObject = new HomePageObject(getDriver());
        homePageObject.toStartPage();

        assertTrue(homePageObject.isOnPage());
    }

    @After
    public void destroySession() {
        getDriver().manage().deleteAllCookies();
        int cookies = getDriver().manage().getCookies().size();
        assertEquals(0, cookies);
    }


    @Test
    public void testCreateNews() throws InterruptedException {
        User u = createUniqueUserThenLogIn();
        assertTrue(homePageObject.isOnPage());
        assertTrue(homePageObject.isLoggedIn(u.getUserName()));

        assertEquals(0, homePageObject.getNumberOfNews(u.getUserName()));

        homePageObject.createNews("someText");
        assertEquals(1, homePageObject.getNumberOfNews(u.getUserName()));

        homePageObject.createNews("someText");
        assertEquals(2, homePageObject.getNumberOfNews(u.getUserName()));
    }

    @Test
    public void testNewsAfterLogout() throws Exception {
        User u = createUniqueUserThenLogIn();

        homePageObject.createNews("someText1");
        homePageObject.createNews("someText2");
        assertEquals(2, homePageObject.getNumberOfNews(u.getUserName()));

        homePageObject.logOut();
        assertEquals(2, homePageObject.getNumberOfNews(u.getUserName()));
    }

    @Test
    public void testUserDetails() throws Exception {
        User u = createUniqueUserThenLogIn();
        homePageObject.createNews("testtext");
        UserDetailsPageObject userDetails = homePageObject.toUserDetails(u.getUserName());
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

    @Test
    public void testScore() throws Exception {
        User u = createUniqueUserThenLogIn();
        homePageObject.createNews("coffecoffecoffe");
        homePageObject.sortNewsBy("time");
        assertEquals(0, homePageObject.getScoreForFirstPost());

        homePageObject.voteForFirstPost(1);
        assertEquals(1, homePageObject.getScoreForFirstPost());

        homePageObject.voteForFirstPost(-1);
        assertEquals(-1, homePageObject.getScoreForFirstPost());

        homePageObject.voteForFirstPost(0);
        assertEquals(0, homePageObject.getScoreForFirstPost());
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

        boolean sorted = homePageObject.isSortedByScore();
        assertFalse(sorted);

        homePageObject.sortNewsBy("score");
        sorted = homePageObject.isSortedByScore();
        assertTrue(sorted);

        homePageObject.sortNewsBy("time");
        sorted = homePageObject.isSortedByScore();
        assertFalse(sorted);
    }

    @Test
    public void testCreateComment() throws Exception {
        User u1 = createUniqueUserThenLogIn();
        homePageObject.createNews("THIS IS THE ONE");
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

    // gå til news bare ved long text
    @Test
    public void canModerate() throws Exception {
        User u1 = createUniqueUserThenLogIn();

        homePageObject.createNews("1");
        System.out.println("made it throug creating news");

        NewsDetailsPageObject newsDetailsPage = homePageObject.toNewsDetails(u1.getUserName());
        System.out.println("made it through trying to go to news details");
        assertTrue(newsDetailsPage.isOnPage());
        System.out.println("made it through is on newspage");
        newsDetailsPage.createComment("this is comment");
        boolean moderated = newsDetailsPage.moderateCommentByUser(u1.getUserName());
        assertTrue(moderated);

        moderated = newsDetailsPage.moderateCommentByUser(u1.getUserName());
        assertFalse(moderated);

        homePageObject = newsDetailsPage.logOut();
        assertTrue(homePageObject.isOnPage());

        newsDetailsPage = homePageObject.toNewsDetails(u1.getUserName());
        moderated = newsDetailsPage.moderateCommentByUser(u1.getUserName());
        assertFalse(moderated);

        homePageObject = newsDetailsPage.goHome();
        createUniqueUserThenLogIn();
        newsDetailsPage = homePageObject.toNewsDetails(u1.getUserName());
        moderated = newsDetailsPage.moderateCommentByUser(u1.getUserName());
        assertFalse(moderated);

        homePageObject = newsDetailsPage.logOut();
        loginExistingUser(u1);
        newsDetailsPage = homePageObject.toNewsDetails(u1.getUserName());
        moderated = newsDetailsPage.moderateCommentByUser(u1.getUserName());
        assertTrue(moderated);
    }

    @Test
    public void testKarma() throws Exception {
        User u1 = createUniqueUserThenLogIn();
        homePageObject.createNews("some news again");

        homePageObject.sortNewsBy("time");
        homePageObject.voteForFirstPost(1);

        NewsDetailsPageObject newsDetailsPageObject = homePageObject.toNewsDetails(u1.getUserName());
        assertTrue(newsDetailsPageObject.isOnPage());

        newsDetailsPageObject.createComment("comment1");
        newsDetailsPageObject.createComment("comment2");

        newsDetailsPageObject.moderateCommentByUser(u1.getUserName());
        homePageObject = newsDetailsPageObject.goHome();
        UserDetailsPageObject userDetailsPageObject = homePageObject.toUserDetails(u1.getUserName());
        assertTrue(userDetailsPageObject.isOnPage());

        int karmaPoints = userDetailsPageObject.getKarmaPoints();
        assertEquals(-19, karmaPoints);
    }

    ////////////////////////////////////////////
    //////////some early stage tests////////////
    ////////////////////////////////////////////

    @Test
    public void testIsHome() throws Exception {
        assertTrue(homePageObject.isOnPage());
    }

    @Test
    public void testLoginLink() throws Exception {
        LoginPageObject loginPageObject = homePageObject.toLogin();

        assertTrue(loginPageObject.isOnPage());
    }

    @Test
    public void testLoginWrongUser() throws Exception {
        LoginPageObject loginPageObject = homePageObject.toLogin();

        assertTrue(loginPageObject.isOnPage());

        String wrongCredentials = "test";

        loginPageObject.login(wrongCredentials, wrongCredentials);

        assertTrue(loginPageObject.isOnPage());
    }


    @Test
    public void testLogin() throws Exception {
        User u = createUniqueUserThenLogIn();
        assertTrue(homePageObject.isOnPage());
        assertTrue(homePageObject.isLoggedIn(u.getUserName()));

        homePageObject = homePageObject.logOut();
        assertTrue(homePageObject.isOnPage());
        assertTrue(homePageObject.isLoggedOut());

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
}