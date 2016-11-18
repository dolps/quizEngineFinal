package com.woact.dolplads.quiz.rest.backend.service;

import com.woact.dolplads.quiz.rest.backend.entity.Category;
import com.woact.dolplads.quiz.rest.backend.repository.CRUD;
import com.woact.dolplads.quiz.rest.backend.repository.CategoryRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by dolplads on 14/11/2016.
 */
@Stateless
public class CategoryService implements CRUD<Long, Category> {
    @Inject
    private CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void remove(Category entity) {
        categoryRepository.remove(entity);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.update(category);
    }
}
