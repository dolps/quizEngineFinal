package com.javaee2.dolplads.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.javaee2.dolplads.GameApplication;
import com.javaee2.dolplads.GameConfiguration;
import com.javaee2.dolplads.IntegrationTestUtils;
import com.javaee2.dolplads.core.Game;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.woact.dolplads.quiz.backend.entity.Question;
import com.woact.dolplads.quiz.backend.entity.Quiz;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

import org.junit.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by dolplads on 27/11/2016.
 */
public class QuizServiceResourceTest {
    @ClassRule
    public static final DropwizardAppRule<GameConfiguration> RULE =
            new DropwizardAppRule<>(GameApplication.class, ResourceHelpers.resourceFilePath("game.yml"));
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    @BeforeClass
    public static void setupClass() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9000;
        RestAssured.basePath = "/app/api/games";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void createGame() throws Exception {
        GsonJsonProvider provider = new GsonJsonProvider();
        Game gameObject = new Game("yo boy");

        String toJson = provider.toJson(gameObject);
        stubRandomQuizzes(toJson);

        given()
                .contentType(ContentType.JSON)
                .queryParam("nu", 3)
                .body(gameObject)
                .post("/create")
                .then()
                .statusCode(200);
    }

    // stubs the endpoint quiz/api/randomQuizzes
    private void stubRandomQuizzes(String json) {
        stubFor(post(urlEqualTo("/quiz/api/randomQuizzes"))
                .withHeader("Content-Type", WireMock.equalTo("application/json"))
                //.withRequestBody(WireMock.equalTo(toJson))
                .willReturn(aResponse()
                        .withBody(json)
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));
    }
}