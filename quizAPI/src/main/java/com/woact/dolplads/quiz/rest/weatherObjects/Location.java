package com.woact.dolplads.quiz.rest.weatherObjects;

import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class Location {


    @XmlElement(name = "time")
    ArrayList<Time> time;

    @Override
    public String toString() {
        return "Location{" +
                "times=" + time +
                '}';
    }
}