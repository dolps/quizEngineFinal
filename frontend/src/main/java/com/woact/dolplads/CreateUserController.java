package com.woact.dolplads;

import com.woact.dolplads.entity.User;
import com.woact.dolplads.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dolplads on 13/10/2016.
 */
@Model
public class CreateUserController {
    @Inject
    private Logger logger;
    @EJB
    private UserService userService;
    @Inject
    private LoginController loginController;
    @Inject
    private User user;
    private String confirmPassword;


    public String create() {
        logger.log(Level.INFO, user.toString());
        if (user.getPasswordHash().equals(confirmPassword)) {
            User persisted = userService.save(user);
            persisted.setLoggedIn(true);
            loginController.setSessionUser(persisted);
        }

        return "home.xhtml";
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
