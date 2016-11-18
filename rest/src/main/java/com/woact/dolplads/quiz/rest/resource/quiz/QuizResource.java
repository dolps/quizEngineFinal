package com.woact.dolplads.quiz.rest.resource;

import com.google.common.base.Throwables;
import com.woact.dolplads.quiz.rest.backend.entity.Category;
import com.woact.dolplads.quiz.rest.backend.entity.CategoryLevel;
import com.woact.dolplads.quiz.rest.backend.entity.Quiz;
import com.woact.dolplads.quiz.rest.backend.entity.SubCategory;
import com.woact.dolplads.quiz.rest.backend.service.QuizService;
import com.woact.dolplads.quiz.rest.contract.QuizRestApi;
import com.woact.dolplads.quiz.rest.dto.CategoryConverter;
import com.woact.dolplads.quiz.rest.dto.CategoryDto;
import com.woact.dolplads.quiz.rest.dto.QuizConverter;
import com.woact.dolplads.quiz.rest.dto.QuizDto;
import io.swagger.annotations.ApiParam;

import javax.ejb.EJB;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dolplads on 07/11/2016.
 */
@SuppressWarnings("unchecked")
public class QuizResource implements QuizRestApi {
    @EJB
    private QuizService quizService;

    @Override
    public List<CategoryDto> addDummies() {
        quizService.createCategory(new Category("this is text"));
        quizService.createCategory(new Category("this is text2"));

        List<Category> categories2 = quizService.findAllCategories();

        return CategoryConverter.transform(categories2);
    }

    @Override
    public List<CategoryDto> categories() {
        return CategoryConverter.transform(quizService.findAllCategories());
    }

    @Override
    public Long createCategory(CategoryDto category) {
        if (category.id != null) {
            throw new WebApplicationException("Cannot specify id for a newly generated news", 400);
        }
        Category c;
        try {
            c = quizService.createCategory(new Category(category.categoryText));
        } catch (Exception e) {
            throw wrapException(e);
        }
        return c.getId();
    }

    @Override
    public void removeCategory(Long id) {
        boolean removed = quizService.removeCategory(id);
        if (!removed) {
            throw new WebApplicationException("unable to remove category with id: " + id, 404);
        }
    }

    @Override
    public Category findCategoryById(@ApiParam(ID_PARAM) Long id) {
        return quizService.findCategoryById(id);
    }

    @Override
    public SubCategory findSubCategoryById(@ApiParam(ID_PARAM) Long id) {
        return quizService.findSubCategoryById(id);
    }

    @Override
    public List<SubCategory> findSubCategories() {
        return quizService.findAllSubCategories(CategoryLevel.SubCategory);
    }

    @Override
    public List<SubCategory> findSubSubCategories() {
        return quizService.findAllSubCategories(CategoryLevel.SubSubCategory);
    }

    @Override
    public SubCategory findSubSubCategoryById(@ApiParam(ID_PARAM) Long id) {
        return null;
    }

    @Override
    public List<QuizDto> findQuizzes() {
        return QuizConverter.transform(quizService.findAll());
    }

    @Override
    public Long createQuiz(QuizDto quizDto) {
        if (quizDto.id != null) {
            throw new WebApplicationException("Cannot specify id for a newly generated quiz", 400);
        }
        Long id;
        try {
            Quiz created = quizService.createQuiz(new Quiz(quizDto.subsubCategory, quizDto.question, quizDto.answerSet));
            id = created.getId();
        } catch (Exception e) {
            throw wrapException(e);
        }

        return id;
    }


    //----------------------------------------------------------


        /*
            Errors:
            4xx: the user has done something wrong, eg asking for something that does not exist (404)
            5xx: internal server error (eg, could be a bug in the code)
         */

    private WebApplicationException wrapException(Exception e) throws WebApplicationException {
        Throwable cause = Throwables.getRootCause(e);
        if (cause instanceof ConstraintViolationException) {
            return new WebApplicationException("Invalid constraints on input: " + cause.getMessage(), 400);
        } else {
            return new WebApplicationException("Internal error", 500);
        }
    }

}
