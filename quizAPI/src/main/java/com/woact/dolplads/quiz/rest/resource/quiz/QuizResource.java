package com.woact.dolplads.quiz.rest.resource.quiz;

import com.google.common.base.Throwables;
import com.woact.dolplads.quiz.backend.entity.Question;
import com.woact.dolplads.quiz.backend.entity.Quiz;
import com.woact.dolplads.quiz.backend.entity.SubCategory;
import com.woact.dolplads.quiz.backend.entity.SubSubCategory;
import com.woact.dolplads.quiz.backend.service.QuizService;
import com.woact.dolplads.quiz.rest.dto.quiz.QuizConverter;
import com.woact.dolplads.quiz.rest.dto.quiz.QuizDto;

import javax.ejb.EJB;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Objects;

/**
 * Created by dolplads on 07/11/2016.
 */
@SuppressWarnings("unchecked")
public class QuizResource implements QuizRestApi {
    @EJB
    private QuizService quizService;

    @Override
    public List<QuizDto> get() {
        return null;
    }

    @Override
    public Long create(QuizDto quizDto) {
        if (quizDto.id != null) {
            throw new WebApplicationException("cannot specify id for a newly created category", 400);
        }
        Long id;
        try {
            SubSubCategory subSubCategory = new SubSubCategory();
            subSubCategory.setId(quizDto.subsubCategoryId);
            id = quizService.save(new Quiz(quizDto.question, quizDto.answerSet, subSubCategory)).getId();
        } catch (Exception e) {
            throw wrapException(e);
        }

        return id;
    }

    @Override
    public QuizDto getById(Long id) {
        return QuizConverter.transform(quizService.findById(id));
    }

    @Override
    public void update(Long id, QuizDto quizDto) {
        if (!Objects.equals(id, quizDto.id)) {
            throw new WebApplicationException("Not allowed to change the id of the resource", 409); // 409 (Conflict)
        }

        Quiz persisted = quizService.findById(id);
        if (persisted == null) {
            throw new WebApplicationException
                    ("Not allowed to update a quiz with PUT, and cannot find quiz with id: " + id, 404);
        }

        try {
            SubSubCategory cat = new SubSubCategory();
            cat.setId(quizDto.id);
            Quiz toUpdate = new Quiz(quizDto.question, quizDto.answerSet, cat);
            toUpdate.setId(id);
            quizService.update(toUpdate);
        } catch (Exception e) {
            throw wrapException(e);
        }
    }

    @Override
    public void delete(Long id) {
        Quiz q = quizService.findById(id);
        if (q == null) {
            throw new WebApplicationException("Not allowed to delete, cannot find quiz with id: " + id, 404);
        }
        quizService.remove(q);
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
