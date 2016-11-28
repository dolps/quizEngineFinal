package com.javaee2.dolplads.resources;

import com.javaee2.dolplads.core.Game;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by dolplads on 26/11/2016.
 */

public interface GameApi {

    List<Game> getActiveGames(@QueryParam("n") int x);

    @POST
    Response createGame(@DefaultValue("5") @QueryParam("n") int x);
}
