package com.woact.dolplads.repository;

import java.util.List;

/**
 * Created by dolplads on 12/10/2016.
 */
public interface CRUD<E, T> {
    T save(T entity);

    T findById(E id);

    void remove(T entity);

    List<T> findAll();


}
