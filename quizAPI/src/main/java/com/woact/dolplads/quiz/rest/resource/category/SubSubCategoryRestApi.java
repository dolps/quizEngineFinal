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
 * Created by dolplads on 21/11/2016.
 */
// todo: add patch method
@Api(value = "/subsubcategories", description = "CRUD action for subsubCategories")
@Path("/subsubcategories")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON)
public interface SubSubCategoryRestApi {
    String ID_PARAM = "numeric id of subsubcategory";


    @GET
    @ApiOperation("Find all subsubCategories")
    List<SubSubCategoryDto> getCategories();

    @POST
    @ApiOperation("Create a subsubCategoryId")
    Long create(@ApiParam("Text of the subCategory, should not specify id") SubSubCategoryDto subCategory);

    @GET
    @Path("id/{id}")
    SubSubCategoryDto getById(@ApiParam(ID_PARAM) @PathParam("id") Long id);

    @PUT
    @Path("id/{id}")
    void update(@ApiParam(ID_PARAM) @PathParam("id") Long id,
                @ApiParam("The updated subsubCategoryId, id cannot be changed") SubSubCategoryDto categoryDto);


    @ApiOperation("Delete a subsubCategoryId with the given id")
    @DELETE
    @Path("id/{id}")
    void delete(@ApiParam(ID_PARAM) @PathParam("id") Long id);
}
