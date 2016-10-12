package com.woact.dolplads.repository;

import com.woact.dolplads.constraints.Repository;
import com.woact.dolplads.entity.User;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by dolplads on 12/10/2016.
 * Extension of the crudrepository
 * manages userspesific DB handling
 */
@SuppressWarnings("unchecked")
@Repository
public class UserRepository extends CrudRepository<Long, User> {


    public UserRepository() {
        super(User.class);
    }

    /**
     * looks up a user by its username
     *
     * @param userName must be a non null String
     * @return if found
     */
    public User findByUserName(String userName) {
        List<User> users = entityManager.createNamedQuery(User.FIND_BY_USERNAME)
                .setParameter("userName", userName)
                .getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
