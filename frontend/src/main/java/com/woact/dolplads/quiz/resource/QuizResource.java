package com.woact.dolplads.quiz.resource;

import com.woact.dolplads.quiz.backend.entity.Category;
import sun.awt.CharsetString;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dolplads on 07/11/2016.
 */
@Path("/quiz")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON)
public class QuizResource {
    @GET
    @Path("categories")
    public List<Category> categories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("categorytext"));

        return categories;
    }

    @POST
    @Path("categories")
    public Response createCategory(@Valid Category category) {
        System.out.println(category.getCategoryText());

        return Response.status(201).entity(category.getCategoryText()).build();
    }
}
