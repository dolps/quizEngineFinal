package com.woact.dolplads.quiz.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dolplads on 26/10/2016.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class AnswerSet {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    List<Answer> answers;

    public AnswerSet(Answer a1, Answer a2, Answer a3, Answer a4) {
        this.answers = new ArrayList<>(Arrays.asList(a1, a2, a3, a4));

        int hasCorrectAnswer = Math.toIntExact(answers.stream().map(Answer::isCorrect).filter(cor -> cor).count());
        if (hasCorrectAnswer != 1) {
            throw new RuntimeException("A answerset should have 1 correct answer");
        }
    }

}
