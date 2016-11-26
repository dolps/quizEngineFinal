package com.woact.dolplads.quiz.rest.resource.quiz;

import com.woact.dolplads.quiz.rest.dto.quiz.QuizDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by dolplads on 07/11/2016.
 */
@Api(value = "/quizzes", description = "Handling of creating and retrieving quizzes")
@Path("/quizzes")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON)
public interface QuizRestApi extends CrudRestApi {
    String ID_PARAM = "numeric id of quiz";

    @GET
    @ApiOperation("Find all quizzes")
    List<QuizDto> get();

    @POST
    @ApiOperation("Create a quiz")
    Long create(@ApiParam("The quiz to create") QuizDto quizDto);

    @GET
    @Path("id/{id}")
    QuizDto getById(@ApiParam(ID_PARAM) @PathParam("id") Long id);

    @PUT
    @Path("id/{id}")
    void update(@ApiParam(ID_PARAM) @PathParam("id") Long id,
                @ApiParam("The updated quiz, id cannot be changed") QuizDto quizDto);


    @ApiOperation("Delete a quiz with the given id")
    @DELETE
    @Path("id/{id}")
    void delete(@ApiParam(ID_PARAM) @PathParam("id") Long id);
}
