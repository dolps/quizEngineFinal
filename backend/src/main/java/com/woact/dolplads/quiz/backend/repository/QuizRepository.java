package com.woact.dolplads.quiz.backend.repository;

import com.woact.dolplads.quiz.backend.entity.Quiz;
import com.woact.dolplads.quiz.backend.entity.SubCategory;

import javax.inject.Inject;

/**
 * Created by dolplads on 21/11/2016.
 */
public class QuizRepository extends CrudRepository<Long, Quiz> {
    public QuizRepository() {
        super(Quiz.class);
    }
}
