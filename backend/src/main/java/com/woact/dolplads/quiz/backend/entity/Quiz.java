package com.woact.dolplads.quiz.rest.backend.entity;

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
@Entity
public class Quiz {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    private SubCategory subsubCategory;

    @OneToOne(cascade = CascadeType.ALL)
    private Question question;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AnswerSet answerSet;

    public Quiz(SubCategory subsubCategory, Question question, AnswerSet answerSet) {
        if (subsubCategory.getCategoryLevel() != 2) {
            throw new IllegalArgumentException("not a level 2 category(sub-sub) level given: "
                    + subsubCategory.getCategoryLevel());
        }
        this.subsubCategory = subsubCategory;
        this.question = question;
        this.answerSet = answerSet;
    }

    //// TODO: 03/11/2016 refactor??
    public boolean checkIfCorrect(int userAnswer) {
        return answerSet.answers.get(userAnswer).isCorrect();
    }
}
