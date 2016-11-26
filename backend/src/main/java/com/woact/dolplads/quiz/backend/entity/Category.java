package com.woact.dolplads.quiz.backend.entity;

import lombok.*;

import javax.persistence.Entity;

/**
 * Created by dolplads on 03/11/2016.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Category extends SuperCategory {
    public Category(String categoryText) {
        super(categoryText);
    }
}
