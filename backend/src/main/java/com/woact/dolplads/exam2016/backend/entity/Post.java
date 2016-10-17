package com.woact.dolplads.exam2016.backend.entity;

import com.woact.dolplads.exam2016.backend.annotations.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by dolplads on 17/10/2016.
 */
@Entity
@Getter
@Setter
public class Post extends AbstractPost {
    public Post() {
    }

    public Post(User user, String text) {
        super(user, text);
    }
}
