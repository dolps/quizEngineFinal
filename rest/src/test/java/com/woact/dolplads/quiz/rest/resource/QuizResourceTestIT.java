package com.woact.dolplads.quiz.rest.resource;

import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by dolplads on 12/11/2016.
 */
public class QuizResourceTestIT {
    @Test
    public void categories() throws Exception {

    }

    @Test
    public void createCategory() throws Exception {
        get("quiz/api/quiz/dummies");
        get("quiz/api/quiz/categories").then().body("id", hasItems(1, 2));
    }

}