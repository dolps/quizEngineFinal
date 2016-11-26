package com.woact.dolplads.quiz.rest.weatherObjects;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Getter
@Setter
public class MetnoOceanForecast {
    private String gmlid;
    private MoxValidTime moxvalidTime;
    private MoxSeaTemperature moxseaTemperature;
    private String moxseaIcePresence;
    private MoxMeanTotalWaveDirection moxmeanTotalWaveDirection;
    private MoxSignificantTotalWaveHeight moxsignificantTotalWaveHeight;
    private MoxSeaCurrentDirection moxseaCurrentDirection;
    private MoxSeaCurrentSpeed moxseaCurrentSpeed;
}