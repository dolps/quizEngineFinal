package com.woact.dolplads.exam2016.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by dolplads on 17/10/2016.
 */

@Entity
@Getter
@Setter
public class Comment extends AbstractPost {
    private boolean moderated;
    private final String moderatedText = "This coment has been moderated";

    public Comment() {
    }

    public Comment(User user, String text) {
        super(user, text);
    }


    public String getText() {
        if (!moderated) {
            return super.getText();
        }

        return moderatedText;
    }

    public int getScore() {
        if (moderated) {
            return -10;
        } else {
            return super.getScore();
        }
    }
}
