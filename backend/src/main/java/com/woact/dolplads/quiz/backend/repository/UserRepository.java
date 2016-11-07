package com.woact.dolplads.quiz.backend.repository;

import com.woact.dolplads.quiz.backend.annotations.Repository;
import com.woact.dolplads.quiz.backend.entity.User;

/**
 * Created by dolplads on 12/10/2016.
 * Extension of the crudrepository
 * manages userspesific DB handling
 */
@Repository
public class UserRepository extends CrudRepository<String, User> {
    public UserRepository() {
        super(User.class);
    }
}
