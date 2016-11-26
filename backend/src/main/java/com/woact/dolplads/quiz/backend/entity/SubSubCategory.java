package com.woact.dolplads.quiz.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by dolplads on 15/11/2016.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class SubSubCategory extends SuperCategory {
    @ManyToOne
    @JoinColumn
    private SubCategory parentCategory;
    @OneToOne
    private Quiz quiz;


    public SubSubCategory(SubCategory parentCategory, String categoryText) {
        super(categoryText);
        this.parentCategory = parentCategory;
    }
}
