package com.woact.dolplads.quiz.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by dolplads on 03/11/2016.
 */
@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    private boolean isCorrect;
    private String answerText;

    public Answer(boolean isCorrect, String answerText) {
        this.isCorrect = isCorrect;
        this.answerText = answerText;
    }

    public Answer() {
    }
}
