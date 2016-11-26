package com.woact.dolplads.quiz.backend.service;

import com.woact.dolplads.quiz.backend.entity.Category;
import com.woact.dolplads.quiz.backend.entity.Quiz;
import com.woact.dolplads.quiz.backend.entity.SubCategory;
import com.woact.dolplads.quiz.backend.repository.CategoryRepository;
import com.woact.dolplads.quiz.backend.repository.QuizRepository;
import lombok.NoArgsConstructor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by dolplads on 03/11/2016.
 */
@SuppressWarnings("unchecked")
@Stateless
@NoArgsConstructor
public class QuizService extends CommonCRUDService<Long, Quiz> {
    private QuizRepository quizRepository;

    @Inject
    public QuizService(QuizRepository quizRepository) {
        super(quizRepository);
        this.quizRepository = quizRepository;
    }
}
