package com.woact.dolplads.quiz.rest.contract;

import com.woact.dolplads.quiz.rest.backend.entity.Category;
import com.woact.dolplads.quiz.rest.backend.entity.Quiz;
import com.woact.dolplads.quiz.rest.backend.entity.SubCategory;
import com.woact.dolplads.quiz.rest.dto.CategoryDto;
import com.woact.dolplads.quiz.rest.dto.QuizDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by dolplads on 07/11/2016.
 */
@Api(value = "/quiz", description = "Handling of creating and retrieving quizparts")
@Path("/quiz")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON)
public interface QuizRestApi {
    String ID_PARAM = "numeric id of category";

    @ApiOperation("Insert some dummies")
    @GET
    @Path("dummies")
    List<CategoryDto> addDummies();

    @ApiOperation(value = "Get all the categories")
    @GET
    @Path("categories")
    List<CategoryDto> categories();

    @ApiOperation("Create a category")
    @ApiResponse(code = 200, message = "The id of newly created news")
    @POST
    @Path("categories")
    Long createCategory(
            @ApiParam("the categoryText, no need to specify id")
            @Valid CategoryDto category);

    @ApiOperation("Remove category")
    @ApiResponse(code = 200, message = "category was removed")
    @DELETE
    @Path("categories/{id}")
    void removeCategory(@ApiParam(ID_PARAM) @PathParam("id") Long id);


    @ApiOperation("find category by id")
    @GET
    @Path("categories/{id}")
    Category findCategoryById(@ApiParam(ID_PARAM) @PathParam("id") Long id);


    @ApiOperation("find subcategory by id")
    @GET
    @Path("subcategories/{id}")
    SubCategory findSubCategoryById(@ApiParam(ID_PARAM) @PathParam("id") Long id);

    @ApiOperation("Find all subCategories")
    @GET
    @Path("subcategories")
    List<SubCategory> findSubCategories();

    @ApiOperation("Find all subsubCategories")
    @GET
    @Path("subsubcategories")
    List<SubCategory> findSubSubCategories();

    @ApiOperation("Find all subsubCategories by id")
    @GET
    @Path("subsubcategories/{id}")
    SubCategory findSubSubCategoryById(@ApiParam(ID_PARAM) @PathParam("id") Long id);

    @ApiOperation("find all quizzes")
    @GET
    @Path("quizzes")
    List<Quiz> findQuizzes();

    @ApiOperation("create a quiz")
    @POST
    @Path("quizzes")
    @ApiResponse(code = 200, message = "The id of newly created news")
    Long createQuiz(@ApiParam("Text of news, plus author id and country. Should not specify id or creation time")
                            QuizDto quizDto);
}
