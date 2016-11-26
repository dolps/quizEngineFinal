package com.woact.dolplads.quiz.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by dolplads on 26/10/2016.
 */
@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Question {
    private String questionTxt;

    public Question(String questionTxt) {
        this.questionTxt = questionTxt;
    }
}
