package com.woact.dolplads;

import com.woact.dolplads.entity.User;
import com.woact.dolplads.service.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dolplads on 12/10/2016.
 */
@SessionScoped
@Named
public class LoginController implements Serializable {
    private User sessionUser;
    @EJB
    private UserService userService;
    @Inject
    private CredentialsController credentials;


    public String logIn() {
        User user = userService.login(credentials.getUserName(), credentials.getPassword());

        if (user != null) {
            sessionUser = user;
            sessionUser.setLoggedIn(true);

            return "home.xhtml";
        }

        return null;
    }

    public String logOut() {
        sessionUser = null;
        return "home.xhtml";
    }

    public User getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }
}
