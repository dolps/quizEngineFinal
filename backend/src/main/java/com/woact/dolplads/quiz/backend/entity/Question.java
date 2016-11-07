package com.woact.dolplads.quiz.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by dolplads on 26/10/2016.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private String question;

    public Question(String question) {
        this.question = question;
    }
}
