package com.woact.dolplads.exam2016.frontend.controller;

import com.woact.dolplads.exam2016.backend.entity.AbstractPost;
import com.woact.dolplads.exam2016.backend.entity.Post;
import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.backend.entity.Vote;
import com.woact.dolplads.exam2016.backend.service.PostEJB;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

/**
 * Created by dolplads on 17/10/2016.
 */

@Log
@SessionScoped
@Named
public class NewsController implements Serializable {
    @Inject
    private LoginController loginController;
    @EJB
    private PostEJB postEJB;
    private Post post;

    private int someVal = 1;
    private String sortValue = "time";
    private String voteValue;

    private String newsSortOption;

    private Map<Long, Integer> userVoteMap;
    private ListDataModel<Post> listDataModel;

    String xxx;

    public ListDataModel<Post> getListDataModel() {
        List<Post> posts = getPostsBySortValue();


        User sessionUser = loginController.getSessionUser();
        if (sessionUser != null) {
            setMyVote(sessionUser, posts);
        }
        listDataModel = new ListDataModel<>(posts);
        return listDataModel;
    }

    public void setListDataModel(ListDataModel<Post> listDataModel) {
        this.listDataModel = listDataModel;
    }

    @PostConstruct
    public void doConstruct() {
        post = new Post();
        newsSortOption = "BYSCORE";
        userVoteMap = new ConcurrentHashMap<>();
        listDataModel = new ListDataModel<>();

    }

    /*
    public void updatePosts() {
        log.log(Level.INFO, "sortoption: " + newsSortOption);
    }
    */

    /**
     * This is working now i think
     *
     * @param event
     */
    public void valueChanged(ValueChangeEvent event) {
        log.log(Level.INFO, "fired event " + event.toString());
        log.log(Level.INFO, "vote value " + sortValue);
        //do your stuff
    }

    public void myListener(ValueChangeEvent event) {
        int index = listDataModel.getRowIndex();
        Post p = listDataModel.getRowData();
        String value = event.getNewValue().toString();
        postEJB.voteForPost(loginController.getSessionUser().getUserName(), p.getId(), Integer.parseInt(value));
        log.log(Level.INFO, "listIndex: " + index);
    }

    public String doCreate() {
        User current = loginController.getSessionUser();

        Post persisted = postEJB.createPost(current.getUserName(), post);
        postEJB.voteForPost(current.getUserName(), persisted.getId(), 2);

        post = new Post();
        return "home.jsf";
    }

    public void updateVote(Long id, Integer value) {
        System.out.println("TESS: postid:" + id + "  " + value);
        System.out.println("TESS2: " + userVoteMap.get(id) + "  " + value);
        //userVoteMap.put(id, 1);
        postEJB.voteForPost(loginController.getSessionUser().getUserName(), id, value);
    }

    public void updateVote(ValueChangeEvent event) {
        Object values = event.getNewValue();
        System.out.println("valss: " + values.toString());
        String pickedvalue = values.toString();

        userVoteMap.forEach((key, vals) -> {
            log.log(Level.INFO, "valsinmap" + vals);
        });

        User user = loginController.getSessionUser();
        Long postId = 1L;

        if (pickedvalue.equals("1")) {
            postEJB.voteForPost(user.getUserName(), postId, 1);
        } else if (pickedvalue.equals("0")) {
            postEJB.voteForPost(user.getUserName(), postId, 0);
        } else {
            postEJB.voteForPost(user.getUserName(), postId, -1);
        }
    }

    public ListDataModel<Post> displayNews() {
        List<Post> posts = getPostsBySortValue();


        User sessionUser = loginController.getSessionUser();
        if (sessionUser != null) {
            setMyVote(sessionUser, posts);
        }
        listDataModel = new ListDataModel<>(posts);
        return listDataModel;
    }

    private List<Post> getPostsBySortValue() {
        List<Post> posts = new ArrayList<>();
        if (sortValue != null) {
            if (sortValue.equals("time")) {
                posts = postEJB.findAllPostsByTime();
            } else {
                posts = postEJB.findAllPostsByScore();
            }
        } else {
            posts = postEJB.findAllPostsByTime();
        }
        return posts;
    }

    private void setMyVote(User sessionUser, List<Post> posts) {
        posts.stream().map(Post::getId)
                .forEach(postId -> {
                    int val = postEJB.findVoteValueForPost(sessionUser.getUserName(), postId);
                    userVoteMap.put(postId, val);

                });


        /*
        posts.forEach(post -> {
            List<Vote> votes = post.getVotes();
            votes.forEach(vote -> {
                boolean isEqual = vote.getUser().getUserName().equals(sessionUser.getUserName());
                if (isEqual) {
                    post.setValueByUser(vote.getValue());
                    //post.setVoteValueByUser(vote.getVoteValue());
                    System.out.println("is equal");
                }
            });
        });
        */
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getSortValue() {
        return sortValue;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }

    public String getVoteValue() {
        return voteValue;
    }

    public void setVoteValue(String voteValue) {
        this.voteValue = voteValue;
    }

    public String getNewsSortOption() {
        return newsSortOption;
    }

    public void setNewsSortOption(String newsSortOption) {
        this.newsSortOption = newsSortOption;
    }

    public Map<Long, Integer> getUserVoteMap() {
        log.log(Level.INFO, "getting uservotemap");
        return userVoteMap;
    }

    public void setUserVoteMap(Map<Long, Integer> userVoteMap) {
        log.log(Level.INFO, "setting uservotemap");
        this.userVoteMap = userVoteMap;
    }
}
