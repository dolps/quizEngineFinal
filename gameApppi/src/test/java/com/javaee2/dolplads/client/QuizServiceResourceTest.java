package com.javaee2.dolplads.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.javaee2.dolplads.GameApplication;
import com.javaee2.dolplads.GameConfiguration;
import com.javaee2.dolplads.core.Game;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.woact.dolplads.quiz.backend.entity.Question;
import com.woact.dolplads.quiz.backend.entity.Quiz;
import io.dropwizard.client.JerseyClientBuilder;
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
    private static Client client;

    @BeforeClass
    public static void setupClass() {
        client = IntegrationTestUtils.buildClient(RULE);
    }

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);
    String quizServerUrl = "http://localhost:9000";

    @Test
    public void createGame() throws Exception {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9000;
        RestAssured.basePath = "/games";

        GsonJsonProvider provider = new GsonJsonProvider();
        Game g = new Game("yo boy");
        g.setId(2L);
        String toJson = provider.toJson(g);

        System.out.println("convertedjson: " + toJson);

        stubFor(post(urlEqualTo("/quiz/api/randomQuizzes"))
                .withHeader("Content-Type", WireMock.equalTo("application/json"))
                //.withRequestBody(WireMock.equalTo(toJson))
                .willReturn(aResponse()
                        .withBody(toJson)
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));

        given()
                .contentType(ContentType.JSON)
                .and()
                .queryParam("nu", 3)
                .and()
                .body(g)
                .post("/create")
                .then()
                .statusCode(200);
    }


    @Test
    public void createWithoutExternalResourceGame() throws Exception {
        Quiz quiz = new Quiz(new Question("this is text"), null, null);

        Response response = client.target(String.format("http://localhost:%d/games/?n=10", RULE.getLocalPort()))
                .request()
                .post(Entity.entity(quiz, MediaType.APPLICATION_JSON));

        System.out.println("the header" + response.getHeaderString("Location"));
        assertThat(response.getHeaderString("Location")).contains("localhost:9000");
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Ignore
    @Test
    public void getActiveGames() throws Exception {
        Response response = client.target(
                String.format("http://localhost:%d/games/test", RULE.getLocalPort()))
                .request()
                .get();
        ArrayList<Game> x = response.readEntity(ArrayList.class);
        System.out.println(x.toString());
        System.out.println("number of elements " + x.size());

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Ignore
    @Test
    public void getActiveGames2() throws Exception {
        Response response = client.target(
                String.format("http://localhost:%d/games/test", RULE.getLocalPort()))
                .request()
                .get();
        ArrayList<Game> x = response.readEntity(ArrayList.class);
        System.out.println(x.toString());
        System.out.println("number of elements " + x.size());

        assertThat(response.getStatus()).isEqualTo(200);
    }

}