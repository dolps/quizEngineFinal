package com.woact.dolplads.quiz.rest.weatherObjects;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@Getter
@Setter
public class GmlTimePeriod {
    private String gmlid;
    private Date gmlbegin;
    private Date gmlend;
}