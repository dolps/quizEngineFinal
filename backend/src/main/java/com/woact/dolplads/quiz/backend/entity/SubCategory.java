package com.woact.dolplads.quiz.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by dolplads on 03/11/2016.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class SubCategory extends Category {
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    private Category parentCategory;


    public SubCategory(Category parentCategory, String categoryText) {
        super(categoryText);
        this.parentCategory = parentCategory;

        setCategoryLevel(parentCategory.getCategoryLevel() + 1);
        if (getCategoryLevel() >= 3) {
            throw new RuntimeException("cannot add more than 2 sub categories");
        }
    }
}
