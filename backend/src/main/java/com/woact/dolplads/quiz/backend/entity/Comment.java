package com.woact.dolplads.quiz.backend.entity;
import javax.persistence.*;

/**
 * Created by dolplads on 17/10/2016.
 */

@Entity
public class Comment extends AbstractPost {
    private boolean moderated;
    private final String moderatedText = "This coment has been moderated";

    public Comment() {
    }

    public Comment(User user, String text) {
        super(user, text);
    }


    public int getScore() {
        if (moderated) {
            return -10;
        } else {
            return super.getScore();
        }
    }

    public boolean isModerated() {
        return moderated;
    }

    public void setModerated(boolean moderated) {
        this.moderated = moderated;
    }

    public String getModeratedText() {
        return moderatedText;
    }

    public String getText() {
        if (!moderated) {
            return super.getText();
        }


        return moderatedText;
    }
}
