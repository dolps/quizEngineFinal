package com.woact.dolplads.quiz.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.inject.Named;
import javax.persistence.*;

/**
 * Created by dolplads on 26/10/2016.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Quiz {
    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    private Question question;
    @Embedded
    private AnswerSet answerSet;
    @OneToOne(mappedBy = "quiz")
    private SubSubCategory subSubCategory;

    public Quiz(Question question, AnswerSet answerSet, SubSubCategory subsubCategory) {
        this.question = question;
        this.answerSet = answerSet;
        this.subSubCategory = subsubCategory;
    }
}
