package com.woact.dolplads.exam2016.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by dolplads on 17/10/2016.
 */

//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Getter
@Setter
@Table(name = "Comment")
@DiscriminatorValue("Bike")
//@PrimaryKeyJoinColumn(name="POST_PK")
public class Comment extends AbstractPost {

    private boolean moderated;

    @NotNull
    @ManyToOne
    @JoinColumn
    private Post post;

    protected Comment() {
    }

    public Comment(User user, Post post, String text) {
        super(user, text);
        this.post = post;
    }

    public String getText() {
        if (!moderated) {
            return super.getText();
        }

        return "has been moderated";
    }
}
