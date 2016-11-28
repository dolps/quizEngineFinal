package com.javaee2.dolplads.resources;

import com.javaee2.dolplads.core.Game;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dolplads on 26/11/2016.
 */
@Path("/gamesdeprecatedis")
@Produces(MediaType.APPLICATION_JSON)
public class GameResource implements GameApi {

    @GET
    @Override
    public List<Game> getActiveGames(@QueryParam("n") int x) {
        List<Game> games = new ArrayList<>();
        return games;
    }

    /**
     * @param x specifies the  number of quizzes in the game
     *          all quizzes in the game must belong to the same subsubcategory
     *          information about the game must be saved in a database
     * @return
     */
    @POST
    @Override
    public Response createGame(@DefaultValue("5") @QueryParam("n") int x) {
        return null;
    }
}
