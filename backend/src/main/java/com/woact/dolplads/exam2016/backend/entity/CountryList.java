package com.woact.dolplads.exam2016.backend.entity;

import com.woact.dolplads.exam2016.backend.enums.CountryEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dolplads on 02/10/16.
 */
@Getter
public class CountryList {
    private List<CountryEnum> countryEnumList;

    public CountryList() {
        countryEnumList = Arrays.asList(CountryEnum.Denmark, CountryEnum.Norway, CountryEnum.Sweden);
    }
}
