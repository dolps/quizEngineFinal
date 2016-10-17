package com.woact.dolplads.exam2016.backend.service;

import com.woact.dolplads.exam2016.backend.entity.Comment;
import com.woact.dolplads.exam2016.backend.entity.Post;
import com.woact.dolplads.exam2016.backend.entity.User;
import com.woact.dolplads.exam2016.backend.entity.Vote;
import com.woact.dolplads.exam2016.backend.repository.CommentRepository;
import com.woact.dolplads.exam2016.backend.repository.PostRepository;
import com.woact.dolplads.exam2016.backend.repository.UserRepository;
import com.woact.dolplads.exam2016.backend.repository.VoteRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dolplads on 17/10/2016.
 */
@SuppressWarnings("unchecked")
@Stateless
public class PostEJB {
    @EJB
    private PostRepository postRepository;
    @EJB
    private CommentRepository commentRepository;
    @EJB
    private UserRepository userRepository;
    @EJB
    private VoteRepository voteRepository;
    @Inject
    private Logger logger;


    public Post createPost(@NotNull @Valid Post post) {
        return postRepository.save(post);
    }

    public Vote voteForPost(@NotNull String userId, @NotNull Long postId) {
        final int voteValue = 1;
        User user = userRepository.findById(userId);
        Post post = postRepository.findById(postId);

        Vote vote = voteRepository.findByUserAndComment(user.getUserName(), post.getId());

        if (vote == null) {
            vote = voteRepository.save(new Vote(user, post, voteValue));
        } else if (vote.getVoteValue() != voteValue) {
            vote.setVoteValue(voteValue);
            vote = voteRepository.update(vote);
        }

        return vote;
    }

    public Vote voteAgainst(@NotNull String userId, @NotNull Long postId) {
        final int voteValue = -1;
        User user = userRepository.findById(userId);
        Post post = postRepository.findById(postId);

        Vote vote = voteRepository.findByUserAndComment(user.getUserName(), post.getId());

        if (vote == null) {
            vote = voteRepository.save(new Vote(user, post, voteValue));
            logger.log(Level.INFO, "vote saved");
        } else if (vote.getVoteValue() != voteValue) {
            logger.log(Level.INFO, "vote updated");
            vote.setVoteValue(voteValue);
            vote = voteRepository.update(vote);
        }

        return vote;
    }

    public void unVote(@NotNull String userId, @NotNull Long postId) {
        Vote vote = voteRepository.findByUserAndComment(userId, postId);
        voteRepository.remove(vote);
    }

    public List<Post> getAllPostByTime() {
        return postRepository.findAllByTime();
    }

    public List<Post> getAllPostsByScore() {
        return postRepository.findAllByScore();
    }

    public int getScoreForPost(Long postId) {
        int score = 0;
        List<Vote> votes = voteRepository.findByPostId(postId);

        for (Vote vote : votes) {
            score += vote.getVoteValue();
        }
        return score;
    }

    public Comment createComment(@NotNull @Valid Comment comment) {
        return commentRepository.save(comment);
    }

    public void moderateComment(String userId, Long commentId, boolean value) {
        Comment comment = commentRepository.findById(commentId);
        User user = userRepository.findById(userId);

        if (comment != null && user != null && comment.getUser().getUserName().equals(user.getUserName())) {
            comment.setModerated(value);
        }

    }

    // still missing the unvote // TODO: 17/10/2016
    public Vote voteForComment(String userId, Long commentId, int voteValue) {
        User user = userRepository.findById(userId);
        Comment comment = commentRepository.findById(commentId);
        Vote vote = voteRepository.findByUserAndComment(userId, commentId);

        if (vote == null) {
            System.out.println(); // TODO: 17/10/2016 put in its own method
            vote = voteRepository.save(new Vote(user, comment, voteValue));
            logger.log(Level.INFO, "vote saved");
        } else if (vote.getVoteValue() != voteValue) {
            logger.log(Level.INFO, "vote updated");
            vote.setVoteValue(voteValue);
            vote = voteRepository.update(vote);
        }

        return vote;
    }

    /**
     * Karma points are based on a users interaction with the
     * news Enginge
     * its calculated by the score of posts/comments
     * and a moderation of comment gives -10 points
     *
     * @param userName
     * @return
     */
    public int getCarmaPointsForUser(String userName) {
        List<Post> posts = postRepository.findByUser(userName);
        List<Comment> comments = commentRepository.findByUser(userName);
        logger.log(Level.INFO, "number of posts: " + posts.size());
        logger.log(Level.INFO, "number of comments: " + comments.size());
        int voteValue = 0;

        for (Post post : posts) {
            for (Vote vote : post.getVotes()) {
                voteValue += vote.getVoteValue();
            }
        }
        logger.log(Level.INFO, "votevalue after getting posts " + voteValue);

        int commentNr = 1;
        for (Comment comment : comments) {
            logger.log(Level.INFO, "debug: commentNR" + commentNr++);
            if (comment.isModerated()) {
                voteValue -= 10;
            }
            for (Vote vote : comment.getVotes()) {
                logger.log(Level.INFO, "debug: " + comment.getText() + " " + vote.getVoteValue());
                voteValue += vote.getVoteValue();
                logger.log(Level.INFO, "current voteValue: " + voteValue);
            }
        }


        return voteValue;
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId);
    }

    // TODO: 17/10/2016 test
    public List<Comment> findCommentsByPost(Long postId) {
        return commentRepository.findByPost(postId);
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
