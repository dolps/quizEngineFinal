package com.woact.dolplads.exam2016.frontend.controller;

import com.woact.dolplads.exam2016.backend.entity.Comment;
import com.woact.dolplads.exam2016.backend.entity.Post;
import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.backend.service.PostEJB;
import com.woact.dolplads.exam2016.backend.service.UserEJB;
import lombok.extern.java.Log;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by dolplads on 18/10/2016.
 */
@Log
@Named
@SessionScoped
public class NewsDetailsController implements Serializable {
    @Inject
    private LoginController loginController;
    @EJB
    private PostEJB postEJB;
    @EJB
    private UserEJB userEJB;

    private String newsId;

    private Post requestedPost;

    private Comment comment;

    private ListDataModel<Comment> listDataModel;

    private Map<Long, Integer> userVoteMap;

    public void moderate(Long commentId, Boolean moderate) {
        User sessionUser = loginController.getSessionUser();
        postEJB.moderateComment(sessionUser.getUserName(), commentId, moderate);
    }


    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("newsId")) {
            newsId = params.get("newsId");
        }
        if (newsId != null) {
            requestedPost = postEJB.findPost(Long.parseLong(newsId));
            log.log(Level.INFO, "dauser:" + requestedPost.getUser().getUserName());
        }

        comment = new Comment(null, null);
        listDataModel = new ListDataModel<>();
        userVoteMap = new HashMap<>();
    }

    public String doCreate() {
        comment.setUser(loginController.getSessionUser());
        postEJB.createCommentForPost(Long.parseLong(newsId), comment);
        comment = new Comment(null, null);

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        log.log(Level.INFO, "viewID: " + viewId);
        return viewId + "?faces-redirect=true";
    }

    public ListDataModel<Comment> getListDataModel() {
        if (newsId != null) {
            List<Comment> comments = postEJB.findCommentsByPost(Long.parseLong(newsId));

            User sessionUser = loginController.getSessionUser();
            if (sessionUser != null) {
                setMyVote(sessionUser, comments);
            }
            listDataModel = new ListDataModel<>(comments);
        }
        return listDataModel;
    }

    private void setMyVote(User sessionUser, List<Comment> comments) {
        comments.stream().map(Comment::getId)
                .forEach(commentId -> {
                    int val = postEJB.findVoteValueForComment(sessionUser.getUserName(), commentId);
                    userVoteMap.put(commentId, val);
                });
    }

    public void voteListener(ValueChangeEvent event) {
        int index = listDataModel.getRowIndex();
        Comment c = listDataModel.getRowData();
        String value = event.getNewValue().toString();
        postEJB.voteForComment(loginController.getSessionUser().getUserName(), c.getId(), Integer.parseInt(value));
    }

    public void setListDataModel(ListDataModel<Comment> listDataModel) {
        this.listDataModel = listDataModel;
    }

    public Map<Long, Integer> getUserVoteMap() {
        return userVoteMap;
    }

    public void setUserVoteMap(Map<Long, Integer> userVoteMap) {
        this.userVoteMap = userVoteMap;
    }

    public Post getRequestedPost() {
        return requestedPost;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
