package com.woact.dolplads.quiz.backend.service;

import com.woact.dolplads.quiz.backend.entity.*;
import com.woact.dolplads.quiz.backend.testUtils.ArquillianTestHelper;
import com.woact.dolplads.quiz.backend.testUtils.DeleterEJB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJB;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 03/11/2016.
 */
public class QuizServiceTest extends ArquillianTestHelper {
    @EJB
    private QuizService quizService;
    @EJB
    private DeleterEJB deleterEJB;

    @Before
    @After
    public void prepareDB() throws Exception {
        System.out.println("initalize");
    }

    @Test
    public void testDoSomething() throws Exception {
        assertTrue(1 == 1);
    }
}