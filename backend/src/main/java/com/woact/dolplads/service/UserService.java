package com.woact.dolplads.service;

import com.woact.dolplads.entity.User;
import com.woact.dolplads.injector.DolpLogger;
import com.woact.dolplads.repository.CRUD;
import com.woact.dolplads.repository.UserRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by dolplads on 12/10/2016.
 */
@Stateless
public class UserService {
    @EJB
    protected UserRepository userRepository;
    @Inject
    private Logger logger;

    public User save(User user) {
        User persisted = userRepository.findByUserName(user.getUserName());

        if (persisted == null) {
            String salt = DigestUtil.getSalt();
            String hash = DigestUtil.computeHash(user.getPasswordHash(), salt);

            user.setPasswordHash(hash);
            user.setSalt(salt);

            return userRepository.save(user);
        }

        return null;
    }

    public User login(@NotNull String userName, @NotNull String password) {
        User persisted = userRepository.findByUserName(userName);

        if (persisted != null) {
            String computedHash = DigestUtil.computeHash(password, persisted.getSalt());
            if (persisted.getPasswordHash().equals(computedHash)) {
                return persisted;
            }
        }

        return null;
    }
}
