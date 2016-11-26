package com.woact.dolplads.quiz.rest.resource.category;

import com.woact.dolplads.quiz.rest.dto.category.SubCategoryDto;
import com.woact.dolplads.quiz.rest.dto.category.SubSubCategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by dolplads on 18/11/2016.
 */
// todo: add patch method
@Api(value = "/subcategories", description = "CRUD action for subCategories")
@Path("/subcategories")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON)
public interface SubCategoryRestApi {
    String ID_PARAM = "numeric id of category";


    @GET
    @ApiOperation("Find all subCategories")
    List<SubCategoryDto> getCategories();

    @POST
    @ApiOperation("Create a subCategory")
    Long create(@ApiParam("Text of the subCategory, should not specify id") SubCategoryDto subCategory);

    @GET
    @Path("id/{id}")
    SubCategoryDto getById(@ApiParam(ID_PARAM) @PathParam("id") Long id);

    @PUT
    @Path("id/{id}")
    void update(@ApiParam(ID_PARAM) @PathParam("id") Long id,
                @ApiParam("The updated subCategory, id cannot be changed") SubCategoryDto categoryDto);


    @ApiOperation("Delete a subCategory with the given id")
    @DELETE
    @Path("id/{id}")
    void delete(@ApiParam(ID_PARAM) @PathParam("id") Long id);

    @Path("parent/{id}")
    @GET
    @ApiOperation("find all subcategories based off of parent id")
    List<SubCategoryDto> getSubCategoriesByParentId(@ApiParam("numeric id of parent category") @PathParam("id") Long id);

    @Path("id/{id}/subsubcategories")
    @GET
    @ApiOperation("find all subcategories based off of parent id")
    List<SubSubCategoryDto> getSubSubCategoriesByParentId(@ApiParam("numeric id of parent category") @PathParam("id") Long id);
}
