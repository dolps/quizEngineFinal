package com.woact.dolplads.quiz.rest.backend.repository;

import com.woact.dolplads.quiz.rest.backend.annotations.Repository;
import com.woact.dolplads.quiz.rest.backend.entity.Category;

/**
 * Created by dolplads on 14/11/2016.
 */
@Repository
public class CategoryRepository extends CrudRepository<Long, Category> {
    public CategoryRepository() {
        super(Category.class);
    }
}
