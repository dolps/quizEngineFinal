package com.woact.dolplads.exam2016.backend.repository;

import com.woact.dolplads.exam2016.backend.annotations.Repository;
import com.woact.dolplads.exam2016.backend.entity.User;

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
