package com.woact.dolplads.quiz.rest.backend.service;

import com.woact.dolplads.quiz.rest.backend.repository.CommentRepository;
import com.woact.dolplads.quiz.rest.backend.repository.PostRepository;
import com.woact.dolplads.quiz.rest.backend.repository.UserRepository;
import com.woact.dolplads.quiz.rest.backend.repository.VoteRepository;
import com.woact.dolplads.quiz.rest.backend.entity.Comment;
import com.woact.dolplads.quiz.rest.backend.entity.Post;
import com.woact.dolplads.quiz.rest.backend.entity.User;
import com.woact.dolplads.quiz.rest.backend.entity.Vote;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by dolplads on 17/10/2016.
 * <p>
 * PostEJB service layer, handles the logic and deligates persistance to the repository
 */
@SuppressWarnings("unchecked")
@Stateless
public class PostEJB {
    @Inject
    private PostRepository postRepository;
    @Inject
    private CommentRepository commentRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private VoteRepository voteRepository;

    public Post createPost(@NotNull String userName, @NotNull Post post) {
        User user = userRepository.findById(userName);
        post.setUser(user);
        user.getPosts().add(post);
        postRepository.save(post);

        return post;
    }

    public Comment createCommentForPost(@NotNull Long postId, @NotNull Comment comment) {
        Post post = postRepository.findById(postId);
        post.getComments().add(comment);

        return commentRepository.save(comment);
    }

    public void voteForPost(@NotNull String userName, @NotNull Long postId, int value) {
        User user = userRepository.findById(userName);
        Post post = postRepository.findById(postId);
        if (user == null || post == null) return;

        Vote vote = voteRepository.findByUserAndPost(userName, postId);
        if (vote != null) {
            vote.setValue(value);
        } else {
            post.getVotes().add(voteRepository.save(new Vote(user, postId, value)));
        }
    }

    public void voteForComment(@NotNull String userName, @NotNull Long commentId, int value) {
        User user = userRepository.findById(userName);
        Comment comment = commentRepository.findById(commentId);
        if (user == null || comment == null) return;

        if (comment.isModerated()) {
            return;
        }

        Vote vote = voteRepository.findByUserAndPost(userName, commentId);
        if (vote != null) {
            vote.setValue(value);
        } else {
            comment.getVotes().add(voteRepository.save(new Vote(user, commentId, value)));
        }
    }

    public void moderateComment(@NotNull String userName, @NotNull Long commentId, boolean value) {
        Comment comment = commentRepository.findById(commentId);
        if (comment.getUser().getUserName().equals(userName)) {
            comment.setModerated(value);
            voteForComment(userName, commentId, 0);
        }
    }

    public int findVoteValueForPost(@NotNull String userName,@NotNull Long postId) {
        Vote v = voteRepository.findByUserAndPost(userName, postId);
        if (v != null) {
            return v.getValue();
        }

        return 0;
    }

    public List<Comment> findCommentsByPost(Long requestedPostId) {
        return postRepository.findCommentsByPost(requestedPostId);
    }

    public Post findPost(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> findAllPostsByTime() {
        return postRepository.findAllPostsByTime();
    }

    public List<Post> findAllPostsByScore() {
        return postRepository.findAllPostsByScore();
    }

    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }
}
