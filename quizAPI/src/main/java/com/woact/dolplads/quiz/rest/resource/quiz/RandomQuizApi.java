package com.woact.dolplads.quiz.rest.resource.quiz;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by dolplads on 26/11/2016.
 */
@Api(value = "/randomQuizzes", description = "Handling of creating and retrieving quizzes")
@Path("/randomQuizzes")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON)
public interface RandomQuizApi {

    @ApiOperation("Get a random quiz based on the given category")
    @ApiResponses({
            @ApiResponse(code = 307, message = "Moved temporarly")
    })
    @GET
    Response getRandomQuiz(@QueryParam("filter") String category);

    @POST
    List<Long> getRandomQuizzes(@QueryParam("n") int number, @QueryParam("filter") String category);
}
