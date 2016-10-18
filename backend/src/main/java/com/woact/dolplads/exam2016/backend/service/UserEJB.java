package com.woact.dolplads.exam2016.backend.service;

import com.woact.dolplads.exam2016.backend.entity.Comment;
import com.woact.dolplads.exam2016.backend.entity.Post;
import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.backend.repository.UserRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dolplads on 12/10/2016.
 */
@Stateless
public class UserEJB {
    @EJB
    private UserRepository userRepository;
    @Inject
    private Logger logger;

    public User save(User user) {
        User persisted = userRepository.findById(user.getUserName());
        logger.log(Level.INFO, "user persisted? " + persisted);
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
        User persisted = userRepository.findById(userName);

        if (persisted != null) {
            String computedHash = DigestUtil.computeHash(password, persisted.getSalt());
            if (persisted.getPasswordHash().equals(computedHash)) {
                persisted.setLoggedIn(true);
                return persisted;
            }
        }

        return null;
    }

    public User findById(String userName) {
        return userRepository.findById(userName);
    }

    public User update(User testUser) {
        return userRepository.update(testUser);
    }

    public int getCarmaPointsForUser(String userName) {
        int points = 0;
        User user = userRepository.findById(userName);
        if (user != null) {
            List<Comment> comments = user.getComments();
            for (Comment comment : comments) {
                points += comment.getScore();
            }

            List<Post> posts = user.getPosts();
            for (Post p : posts) {
                points += p.getScore();
            }

        }
        return points;
    }
}
