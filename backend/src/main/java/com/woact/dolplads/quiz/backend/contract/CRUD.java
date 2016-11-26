package com.woact.dolplads.quiz.backend.contract;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by dolplads on 12/10/2016.
 * CRUD interface
 */
public interface CRUD<E, T> {
    T save(@Valid @NotNull T entity);

    T findById(@NotNull E id);

    void remove(@NotNull T entity);

    T update(@NotNull T entity);

    List<T> findAll();
}
