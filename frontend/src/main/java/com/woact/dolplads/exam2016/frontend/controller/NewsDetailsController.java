package com.woact.dolplads.exam2016.frontend.controller;

import com.woact.dolplads.exam2016.backend.entity.Comment;
import com.woact.dolplads.exam2016.backend.entity.Post;
import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.backend.service.PostEJB;
import com.woact.dolplads.exam2016.backend.service.UserEJB;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * Created by dolplads on 18/10/2016.
 */
@Model
public class NewsDetailsController {
    @EJB
    private PostEJB postEJB;
    @EJB
    private UserEJB userEJB;

    private String newsId;
    private Post requestedPost;

    //private Comment comment;


    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("newsId")) {
            newsId = params.get("newsId");
        }
        if (newsId != null) {
            requestedPost = postEJB.findPost(Long.parseLong(newsId));
        }
        //comment = new Comment();
    }

    public void doCreate() {
        //postEJB.createCommentForPost(Long.parseLong(newsId), comment);
    }


    public Post getRequestedPost() {
        return requestedPost;
    }

    /*
    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
    */
}
