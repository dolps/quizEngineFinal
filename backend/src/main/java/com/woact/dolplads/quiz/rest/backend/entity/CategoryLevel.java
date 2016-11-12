package com.woact.dolplads.quiz.rest.backend.entity;

/**
 * Created by dolplads on 12/11/2016.
 */
public enum CategoryLevel {
    SubCategory(1), SubSubCategory(2);

    private int value;

    CategoryLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
