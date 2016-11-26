package com.woact.dolplads.quiz.rest.resource.util;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

/**
 * Created by dolplads on 16/11/2016.
 */
public class QuizRestTestBase {
    @BeforeClass
    public static void initClass() {

        // RestAssured configs shared by all the tests
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/newsrest/api/news";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
