package com.woact.dolplads.quiz.rest.weatherObjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MoxValidTime {
    private GmlTimePeriod gmlTimePeriod;

    public GmlTimePeriod getGmlTimePeriod() {
        return this.gmlTimePeriod;
    }

    public void setGmlTimePeriod(GmlTimePeriod gmlTimePeriod) {
        this.gmlTimePeriod = gmlTimePeriod;
    }
}