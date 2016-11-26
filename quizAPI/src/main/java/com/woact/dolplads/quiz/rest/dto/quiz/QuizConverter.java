package com.woact.dolplads.quiz.rest.dto.quiz;

import com.woact.dolplads.quiz.backend.entity.Quiz;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dolplads on 12/11/2016.
 */
public class QuizConverter {

    public static QuizDto transform(Quiz entity) {
        Objects.requireNonNull(entity);

        QuizDto dto = new QuizDto();
        dto.id = entity.getId();
        dto.subsubCategoryId = entity.getSubSubCategory().getId();
        dto.answerSet = entity.getAnswerSet();
        dto.question = entity.getQuestion();

        return dto;
    }

    public static List<QuizDto> transform(List<Quiz> quizzes) {
        Objects.requireNonNull(quizzes);

        return quizzes.stream()
                .map(QuizConverter::transform)
                .collect(Collectors.toList());
    }
}
