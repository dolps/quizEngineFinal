package com.woact.dolplads.exam2016.frontend.controller;

import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.backend.service.UserEJB;
import lombok.extern.java.Log;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by dolplads on 18/10/2016.
 */
@Model
@Log
public class UserDetailsController {
    @EJB
    private UserEJB userEJB;
    private String userName;
    private User requestedUser;


    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("username")) {
            userName = params.get("username");
        }

        if (userName != null) {
            requestedUser = userEJB.findById(userName);
        }
    }

    public String getUserName() {
        /*
        Map<String, String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        log.log(Level.INFO, "username: " + params.get("username"));
        return params.get("username");
        */
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getRequestedUser() {
        return requestedUser;
    }

    public void setRequestedUser(User requestedUser) {
        this.requestedUser = requestedUser;
    }

    public int getKarmaPoints() {
        return userEJB.getCarmaPointsForUser(userName);
    }
}
