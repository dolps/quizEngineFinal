package com.woact.dolplads.quiz.rest.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Created by dolplads on 03/11/2016.
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class SuperCategory {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String categoryText;

    public SuperCategory(String categoryText) {
        this.categoryText = categoryText;
    }
}
