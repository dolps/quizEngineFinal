package com.woact.dolplads.quiz.rest.weatherObjects;

import javax.xml.bind.annotation.XmlRootElement;

public class MoxForecast {
    private MetnoOceanForecast metnoOceanForecast;

    public MetnoOceanForecast getMetnoOceanForecast() {
        return this.metnoOceanForecast;
    }

    public void setMetnoOceanForecast(MetnoOceanForecast metnoOceanForecast) {
        this.metnoOceanForecast = metnoOceanForecast;
    }
}