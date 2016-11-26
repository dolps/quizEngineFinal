package com.woact.dolplads.quiz.rest.resource.category;

import com.google.common.base.Throwables;
import com.woact.dolplads.quiz.backend.entity.SubCategory;
import com.woact.dolplads.quiz.backend.entity.SubSubCategory;
import com.woact.dolplads.quiz.backend.service.SubSubCategoryService;
import com.woact.dolplads.quiz.rest.dto.category.SubCategoryConverter;
import com.woact.dolplads.quiz.rest.dto.category.SubSubCategoryConverter;
import com.woact.dolplads.quiz.rest.dto.category.SubSubCategoryDto;
import io.swagger.annotations.ApiParam;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Objects;

/**
 * Created by dolplads on 21/11/2016.
 */
public class SubSubCategoryResource implements SubSubCategoryRestApi {
    @EJB
    private SubSubCategoryService subSubCategoryService;

    @Override
    public List<SubSubCategoryDto> getCategories() {
        return SubSubCategoryConverter.transform(subSubCategoryService.findAll());
    }

    @Override
    public Long create(SubSubCategoryDto subCategory) {
        if (subCategory.id != null) {
            throw new WebApplicationException("cannot specify id for a newly created category", 400);
        }
        Long id;
        try {
            id = subSubCategoryService.save(new SubSubCategory(subCategory.parentCategory, subCategory.categoryText)).getId();
        } catch (Exception e) {
            throw wrapException(e);
        }

        return id;
    }

    @Override
    public SubSubCategoryDto getById(Long id) {
        return SubSubCategoryConverter.transform(subSubCategoryService.findById(id));
    }

    @Override
    public void update(Long id, SubSubCategoryDto categoryDto) {
        if (!Objects.equals(id, categoryDto.id)) {
            throw new WebApplicationException("Not allowed to change the id of the resource", 409); // 409 (Conflict)
        }

        SubSubCategory persisted = subSubCategoryService.findById(id);
        if (persisted == null) {
            throw new WebApplicationException
                    ("Not allowed to update a category with PUT, and cannot find category with id: " + id, 404);
        }

        try {
            SubSubCategory toUpdate = new SubSubCategory(categoryDto.parentCategory, categoryDto.categoryText);
            toUpdate.setId(id);
            subSubCategoryService.update(toUpdate);
        } catch (Exception e) {
            throw wrapException(e);
        }
    }

    @Override
    public void delete(Long id) {
        SubSubCategory persisted = subSubCategoryService.findById(id);
        if (persisted == null) {
            throw new WebApplicationException("Not allowed to delete, cannot find category with id: " + id, 404);
        } else {
            subSubCategoryService.remove(persisted);
        }
    }


    private WebApplicationException wrapException(Exception e) throws WebApplicationException {
        Throwable cause = Throwables.getRootCause(e);
        if (cause instanceof ConstraintViolationException) {
            return new WebApplicationException("Invalid constraints on input: " + cause.getMessage(), 400);
        } else {
            return new WebApplicationException("Internal error", 500);
        }
    }
}
