package com.woact.dolplads.quiz.rest.resource.category;

import com.google.common.base.Throwables;
import com.woact.dolplads.quiz.backend.entity.Category;
import com.woact.dolplads.quiz.backend.service.CategoryService;
import com.woact.dolplads.quiz.rest.dto.category.*;
import io.swagger.annotations.ApiParam;

import javax.ejb.EJB;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Objects;

/**
 * Created by dolplads on 14/11/2016.
 */
@Path("/categories")
public class CategoryResource implements CategoryRestApi {
    @EJB
    private CategoryService categoryService;

    @Override
    public List<CategoryDto> getCategories(QueryFilter filter) {
        System.out.println("filter: " + filter);
        System.out.println(QueryFilter.withQuizzes);
        if (filter == QueryFilter.withQuizzes) {
            System.out.println("with quizzes");
            return CategoryConverter
                    .transform(categoryService
                            .findAllWithAtLeastOneSubCategoryWithAtLeastOneSubSubCategoryWithAtLeastOneQuiz());
        } else {
            return
                    CategoryConverter.transform(categoryService.findAll());
        }
    }

    @Override
    public Long createCategory(CategoryDto categoryDto) {
        if (categoryDto.id != null) {
            throw new WebApplicationException("cannot specify id for a newly created category", 400);
        }
        Long id;
        try {
            id = categoryService.save(new Category(categoryDto.categoryText)).getId();
        } catch (Exception e) {
            throw wrapException(e);
        }
        return id;
    }

    @Override
    public Response deprecatedGetById(Long id) {
        return Response
                .status(Response.Status.MOVED_PERMANENTLY)
                .location(UriBuilder.fromUri("categories")
                        .queryParam("id", id)
                        .build())
                .build();
    }

    @Override
    public CategoryDto getById(@ApiParam(ID_PARAM) Long id) {
        return CategoryConverter.transform(categoryService.findById(id));
    }

    @Override
    public void update(Long id, CategoryDto categoryDto) {
        if (!Objects.equals(id, categoryDto.id)) {
            throw new WebApplicationException("Not allowed to change the id of the resource", 409); // 409 (Conflict)
        }

        Category persisted = categoryService.findById(id);
        if (persisted == null) {
            throw new WebApplicationException
                    ("Not allowed to update a category with PUT, and cannot find category with id: " + id, 404);
        }

        try {
            Category toUpdate = new Category(categoryDto.categoryText);
            toUpdate.setId(id);
            categoryService.update(toUpdate);
        } catch (Exception e) {
            throw wrapException(e);
        }
    }

    @Override
    public void delete(Long id) {
        Category persisted = categoryService.findById(id);
        if (persisted == null) {
            throw new WebApplicationException("Not allowed to delete, cannot find category with id: " + id, 404);
        } else {
            categoryService.remove(persisted);
        }
    }

    /**
     * "Find all categories with at least one subcategory with at least one subsubcategory"
     * with at least one quiz.
     *
     * @return
     */
    @Override
    public Response deprecatedGetCategoriesAssociatedWithQuiz() {
        return Response.status(301)
                .location(UriBuilder.fromUri("news")
                        .queryParam("filter", "whatever").build())
                .build();
    }


    @Override
    public List<SubSubCategoryDto> getSubSubCategoriesAssociatedWithQuiz() {
        return SubSubCategoryConverter.transform(categoryService.findAllSubSubCategoriesWithAtLeastOneQuiz());
    }

    @Override
    public List<SubCategoryDto> getSubCategoriesOfParentCategory(@ApiParam(ID_PARAM) Long id) {
        return SubCategoryConverter.transform(categoryService.findAllSubCategoriesByParentCategory(id));
    }

    private WebApplicationException wrapException(Exception e) throws WebApplicationException {

        /*
            Errors:
            4xx: the user has done something wrong, eg asking for something that does not exist (404)
            5xx: internal server error (eg, could be a bug in the code)
         */

        Throwable cause = Throwables.getRootCause(e);
        if (cause instanceof ConstraintViolationException) {
            return new WebApplicationException("Invalid constraints on input: " + cause.getMessage(), 400);
        } else {
            return new WebApplicationException("Internal error", 500);
        }
    }
}
