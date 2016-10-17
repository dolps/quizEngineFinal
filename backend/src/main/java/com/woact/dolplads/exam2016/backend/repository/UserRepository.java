package com.woact.dolplads.exam2016.backend.repository;

import com.woact.dolplads.exam2016.backend.entity.User;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by dolplads on 12/10/2016.
 * Extension of the crudrepository
 * manages userspesific DB handling
 */
@SuppressWarnings("unchecked")
@Stateless
public class UserRepository extends CrudRepository<String, User> {
    public UserRepository() {
        super(User.class);
    }


    /**
     * looks up a user by its username
     *
     * @param userName must be a non null String
     * @return if found
     */
    /*
    public User findByUserName(String userName) {
        List<User> users = entityManager.createNamedQuery(User.FIND_BY_USERNAME)
                .setParameter("userName", userName)
                .getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
    */
}
