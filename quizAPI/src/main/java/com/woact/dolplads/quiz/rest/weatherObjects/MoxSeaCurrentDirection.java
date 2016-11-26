package com.woact.dolplads.quiz.rest.weatherObjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MoxSeaCurrentDirection
{
  private String uom;

  public String getUom() { return this.uom; }

  public void setUom(String uom) { this.uom = uom; }

  private String text;

  public String getText() { return this.text; }

  public void setText(String text) { this.text = text; }
}
