package com.woact.dolplads.exam2016.frontend.pages;

import com.woact.dolplads.exam2016.frontend.testUtils.PageObject;
import org.openqa.selenium.WebDriver;

/**
 * Created by dolplads on 19/10/2016.
 */
public class NewsDetailsPageObject extends PageObject {
    private String title = "News Details";
    private final String url = getBaseUrl() + "/newsDetails.jsf";

    public void toStartPage() {
        getDriver().get(url);
        waitForPageToLoad();
    }


    public NewsDetailsPageObject(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains(title);
    }

}
