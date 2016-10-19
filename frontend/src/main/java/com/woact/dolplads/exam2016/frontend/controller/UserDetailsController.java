package com.woact.dolplads.exam2016.frontend.controller;

import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.backend.service.UserEJB;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * Created by dolplads on 18/10/2016.
 * <p>
 * Model for the user details page
 */
@Model
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
