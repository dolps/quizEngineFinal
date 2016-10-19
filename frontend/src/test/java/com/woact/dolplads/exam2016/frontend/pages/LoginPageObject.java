package com.woact.dolplads.exam2016.frontend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.woact.dolplads.exam2016.frontend.testUtils.PageObject;

/**
 * Created by dolplads on 01/10/16.
 * <p>
 * Model for loginPage
 */
public class LoginPageObject extends PageObject {
    private String title = "Login";
    private final String url = getBaseUrl() + "/newUser.jsf";

    public void toStartPage() {
        getDriver().get(url);
        waitForPageToLoad();
    }


    public LoginPageObject(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains(title);
    }


    public HomePageObject login(String userName, String password) {
        WebElement userNameElement = getElement("loginForm:userName");
        userNameElement.clear();
        userNameElement.sendKeys(userName);

        WebElement passwordElement = getElement("loginForm:password");
        passwordElement.clear();
        passwordElement.sendKeys(password);

        WebElement submitElement = getElement("loginForm:logIn");
        submitElement.click();

        waitForPageToLoad();

        return new HomePageObject(getDriver());
    }

    private WebElement getElement(String element) {
        return getDriver().findElement(By.id(element));
    }

    public CreateUserPageObject toCreate() {
        WebElement element = getElement("login_createUser");
        element.click();
        waitForPageToLoad();

        return new CreateUserPageObject(getDriver());
    }
}
