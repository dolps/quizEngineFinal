package com.woact.dolplads.quiz.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by dolplads on 03/11/2016.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Category extends SuperCategory {

    private int categoryLevel;

    public Category(String categoryText) {
        super(categoryText);
    }
}
