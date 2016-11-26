package com.woact.dolplads.quiz.backend.service;

import com.woact.dolplads.quiz.backend.entity.Category;
import com.woact.dolplads.quiz.backend.testUtils.ArquillianTestHelper;
import com.woact.dolplads.quiz.backend.testUtils.DeleterEJB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJB;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 14/11/2016.
 */
public class CategoryServiceTest extends ArquillianTestHelper {
    @EJB
    private DeleterEJB deleterEJB;
    @EJB
    private CategoryService categoryService;


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void save() throws Exception {
        assertTrue(1 == 1);
    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void remove() throws Exception {

    }

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

}