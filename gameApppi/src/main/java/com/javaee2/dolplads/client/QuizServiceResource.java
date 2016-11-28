package com.javaee2.dolplads.client;

import com.codahale.metrics.annotation.Timed;
import com.javaee2.dolplads.api.QuizDto;
import com.javaee2.dolplads.core.Game;
import com.javaee2.dolplads.db.GameDAO;
import com.woact.dolplads.quiz.backend.entity.Question;
import com.woact.dolplads.quiz.backend.entity.Quiz;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dolplads on 27/11/2016.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/games")
public class QuizServiceResource {
    private Client client;
    private GameDAO dao;
    private String quizServerUrl;

    public QuizServiceResource(Client client, GameDAO dao) {
        this.client = client;
        this.dao = dao;
        this.quizServerUrl = "http://localhost:8080/quiz/api";
    }

    @Path("create")
    @POST
    @UnitOfWork
    public Response createAGame(@QueryParam("n") int x, Game game) {
        System.out.println("halla " + game.getZapp());
        WebTarget webTarget = client.target(quizServerUrl + "/randomQuizzes");
        Response response = webTarget.request().post(Entity.entity("{\"id\":1,\"zapp\":\"yo boy\",\"quizzes\":[]}", MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            return Response.ok(response.readEntity(String.class), MediaType.APPLICATION_JSON).build();
        }

        return Response.status(404).build();
    }

    // /randomQuizzes?n=y&filter=x
    @POST
    @UnitOfWork
    public Response createGame(@QueryParam("n") int x, Quiz quiz) {
        System.out.println(x);
        System.out.println(quiz.getQuestion().getQuestionTxt());
        List<Quiz> quizzList = new ArrayList<>();
        quizzList.add(new Quiz(new Question("text"), null, null));

        Game game = dao.save(new Game(quizzList));

        return Response.status(201).header("Location", game.getId()).build();
    }


    @GET
    @Path("deprecatedQuiz")
    public String getDeprecatedQuiz() {
        Response response = client.target(quizServerUrl + "/categories")
                .request(MediaType.APPLICATION_JSON)
                .get();

        ArrayList list = response.readEntity(ArrayList.class);
        System.out.println(list.toString());
        return list.toString();
    }

    @Path("test")
    @GET
    @Timed
    @UnitOfWork
    public List<Game> getActiveGames(@QueryParam("n") int x) {
        List<Game> games = new ArrayList<>();
        Game g = dao.save(new Game("halla"));
        games.add(g);

        games = dao.findAll();

        return games;
    }
}
