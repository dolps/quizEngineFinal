package com.woact.dolplads.quiz.rest.resource;

/**
 * Created by dolplads on 25/11/2016.
 */
public interface Formats {

    String BASE_JSON = "application/json; charset=UTF-8";

    //note the "vnd." (which starts for "vendor") and the
    // "+json" (ie, treat it having json structure)
    String V1_NEWS_JSON = "application/vnd.pg6100.news+json; charset=UTF-8; version=1";
}
