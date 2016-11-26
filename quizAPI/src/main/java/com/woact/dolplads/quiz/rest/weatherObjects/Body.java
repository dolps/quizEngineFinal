package com.woact.dolplads.quiz.rest.weatherObjects;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dolplads on 22/11/2016.
 */
@XmlRootElement
@ToString
public class Body {
    @XmlElement
    String body;
}
