package com.woact.dolplads.quiz.rest.resource.category;

import com.google.common.base.Throwables;
import com.woact.dolplads.quiz.backend.entity.SubCategory;
import com.woact.dolplads.quiz.backend.service.SubCategoryService;
import com.woact.dolplads.quiz.rest.dto.category.*;
import io.swagger.annotations.ApiParam;

import javax.ejb.EJB;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Objects;

/**
 * Created by dolplads on 18/11/2016.
 */
public class SubCategoryResource implements SubCategoryRestApi {
    @EJB
    private SubCategoryService subCategoryService;

    @Override
    public List<SubCategoryDto> getCategories() {
        return SubCategoryConverter.transform(subCategoryService.findAll());
    }

    @Override
    public Long create(SubCategoryDto subCategory) {
        if (subCategory.id != null) {
            throw new WebApplicationException("cannot specify id for a newly created category", 400);
        }
        Long id;
        try {
            id = subCategoryService.save(new SubCategory(subCategory.parentCategory, subCategory.categoryText)).getId();
        } catch (Exception e) {
            throw wrapException(e);
        }

        return id;
    }

    @Override
    public SubCategoryDto getById(Long id) {
        return SubCategoryConverter.transform(subCategoryService.findById(id));
    }

    @Override
    public void update(Long id, SubCategoryDto categoryDto) {
        if (!Objects.equals(id, categoryDto.id)) {
            throw new WebApplicationException("Not allowed to change the id of the resource", 409); // 409 (Conflict)
        }

        SubCategory persisted = subCategoryService.findById(id);
        if (persisted == null) {
            throw new WebApplicationException
                    ("Not allowed to update a category with PUT, and cannot find category with id: " + id, 404);
        }

        try {

            SubCategory toUpdate = new SubCategory(categoryDto.parentCategory, categoryDto.categoryText);
            toUpdate.setId(id);
            subCategoryService.update(toUpdate);
        } catch (Exception e) {
            throw wrapException(e);
        }
    }

    @Override
    public void delete(Long id) {
        SubCategory persisted = subCategoryService.findById(id);
        if (persisted == null) {
            throw new WebApplicationException("Not allowed to delete, cannot find category with id: " + id, 404);
        } else {
            subCategoryService.remove(persisted);
        }
    }

    @Override
    public List<SubCategoryDto> getSubCategoriesByParentId(@ApiParam("numeric id of parent category") Long id) {
        return SubCategoryConverter.transform(subCategoryService.findAllByParentId(id));
    }

    @Override
    public List<SubSubCategoryDto> getSubSubCategoriesByParentId(@ApiParam("numeric id of parent category") Long id) {
        return SubSubCategoryConverter.transform(subCategoryService.findAllSubSubCategoriesBySubCategoryId(id));
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
