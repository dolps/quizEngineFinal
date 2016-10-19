package com.woact.dolplads.exam2016.frontend.pages;

import com.woact.dolplads.exam2016.frontend.testUtils.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by dolplads on 18/10/2016.
 */
public class UserDetailsPageObject extends PageObject {
    private String title = "Userdetails";
    private final String url = getBaseUrl() + "/userDetails.jsf";

    public UserDetailsPageObject(WebDriver driver) {
        super(driver);
    }

    public void toStartPage() {
        getDriver().get(url);
        waitForPageToLoad();
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains(title);
    }

    public int getKarmaPoints() {
        WebElement karmaPoints = getDriver().findElement(By.id("karmaPoints"));
        waitForPageToLoad();
        return Integer.parseInt(karmaPoints.getText().replace("Karma: ", ""));
    }
}
