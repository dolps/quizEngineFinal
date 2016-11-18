package com.woact.dolplads.quiz.backend.service;

import com.woact.dolplads.quiz.backend.contract.CRUD;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by dolplads on 15/11/2016.
 */
@NoArgsConstructor
abstract class CRUDService<E, T> implements CRUD<E, T> {

    private CRUD<E, T> repository;

    CRUDService(CRUD<E, T> repo) {
        this.repository = repo;
    }

    @Override
    public T save(T category) {
        return repository.save(category);
    }

    @Override
    public T findById(E id) {
        return repository.findById(id);
    }

    @Override
    public void remove(T entity) {
        repository.remove(entity);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T update(T category) {
        return repository.update(category);
    }
}
