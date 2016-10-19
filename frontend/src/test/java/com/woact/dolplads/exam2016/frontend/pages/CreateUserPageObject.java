package com.woact.dolplads.exam2016.frontend.pages;

import com.woact.dolplads.exam2016.backend.entity.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.woact.dolplads.exam2016.frontend.testUtils.PageObject;

/**
 * Created by dolplads on 02/10/16.
 */
public class CreateUserPageObject extends PageObject {
    private final String title = "Create User";
    private final String url = getBaseUrl() + "/newUser.jsf";

    public CreateUserPageObject(WebDriver driver) {
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

    public User createUser(String userName, String password, String confirmPassword,
                           String firstName, String middleName, String lastName) throws InterruptedException {
        String form = "createForm:";
        getElement(form + "userName").sendKeys(userName);
        getElement(form + "password").sendKeys(password);
        getElement(form + "confirmPassword").sendKeys(confirmPassword);
        getElement(form + "firstName").sendKeys(firstName);
        getElement(form + "lastName").sendKeys(lastName);
        getElement(form + "save").click();

        User u = new User(userName, firstName, middleName, lastName);
        u.setPasswordHash(confirmPassword);
        u.setSalt("salt");

        return u;
    }

    private WebElement getElement(String element) {
        return getDriver().findElement(By.id(element));
    }
}
