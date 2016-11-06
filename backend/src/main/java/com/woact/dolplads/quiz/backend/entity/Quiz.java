package com.woact.dolplads.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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

    @OneToOne
    private Category category;

    @OneToOne
    private Question question;

    public Quiz(Category subsubCategory, Question question) {
        if (subsubCategory.getLevel() != 2) {
            throw new IllegalArgumentException("not a level 2 category(sub-sub) level given: "
                    + subsubCategory.getLevel());
        }

        this.category = subsubCategory;
        this.question = question;
    }
}
