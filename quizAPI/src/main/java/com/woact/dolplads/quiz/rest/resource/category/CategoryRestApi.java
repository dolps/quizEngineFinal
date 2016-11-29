package com.woact.dolplads.quiz.rest.resource.category;

import com.woact.dolplads.quiz.rest.dto.category.CategoryDto;
import com.woact.dolplads.quiz.rest.dto.category.SubCategoryDto;
import com.woact.dolplads.quiz.rest.dto.category.SubSubCategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by dolplads on 14/11/2016.
 */
// todo: add patch method
@Path("/categories")
@Api(value = "/categories", description = "CRUD action for categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CategoryRestApi {
    String ID_PARAM = "numeric id of category";

    @GET
    @ApiOperation("Find all categories apply filter if desired")
    List<CategoryDto> getCategories(@ApiParam("filter") @QueryParam("filter") QueryFilter filter);

    @POST
    @ApiOperation("Create a cateogry")
    Long createCategory(
            @ApiParam("Text of the category, should not specify id")
                    CategoryDto categoryDto);

    @Deprecated
    @ApiOperation("Get category by id")
    @ApiResponse(code = 301, message = "Deprecated URI. moved permanently")
    @GET
    @Path("id/{id}")
    Response deprecatedGetById(@ApiParam(ID_PARAM) @PathParam("id") Long id);

    @GET
    @Path("/{id}")
    CategoryDto getById(@ApiParam(ID_PARAM) @PathParam("id") Long id);

    @GET
    @Path("x")
    public String update2();

    @PUT
    @Path("id/{id}")
    void update(@ApiParam(ID_PARAM) @PathParam("id") Long id,
                @ApiParam("The updated category, id cannot be changed") CategoryDto categoryDto);


    @ApiOperation("Delete a category with the given id")
    @DELETE
    @Path("id/{id}")
    void delete(@ApiParam(ID_PARAM) @PathParam("id") Long id);


    /**
     * @return all quizzes that have at least one subcategory with at least one
     * subsubcategory
     */
    @Deprecated
    @GET
    @ApiOperation("Find all categories with at least one subcategory with at least one subsubcategory" +
            "with at least one quiz.")
    @ApiResponse(code = 301, message = "Deprecated URI. moved permanently")
    @Path("withQuizzes")
    Response deprecatedGetCategoriesAssociatedWithQuiz();

    @GET
    @ApiOperation("Find all subsubcategories with at least one quiz")
    @Path("withQuizzes/subsubcategories")
    List<SubSubCategoryDto> getSubSubCategoriesAssociatedWithQuiz();

    @GET
    @Path("id/{id}/subcategories")
    @ApiOperation("Find all subcategories based of the parent id")
    List<SubCategoryDto> getSubCategoriesOfParentCategory(@ApiParam(ID_PARAM) @PathParam("id") Long id);
}
