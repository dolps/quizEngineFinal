package com.woact.dolplads.exam2016.frontend.pages;

import com.woact.dolplads.exam2016.frontend.testUtils.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by dolplads on 19/10/2016.
 * model for news Details page
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
        System.out.println(getDriver().getTitle());
        return getDriver().getTitle().contains(title);
    }

    public int getNumberOfCommentsByUser(String userName) {
        List<WebElement> elements = getDriver().findElements(
                By.xpath("//table[@id='commentsTable']//tbody//tr[contains(td[2],'" + userName + "')]")
        );
        System.out.println("number of comments: " + elements);

        return elements.size();
    }

    public void createComment(String text) {
        String form = "createCommentForm:";
        getElemById(form + "postText").sendKeys(text);
        getElemById(form + "save").click();
        waitForPageToLoad();
    }


    public boolean moderateCommentByUser(String userName) {
        List<WebElement> elements = getDriver().findElements(
                By.xpath("//table[@id='commentsTable']//tbody//tr[contains(td[1],'" + userName + "')]/td[5]/form/input[@type='checkbox']")
        );
        if (elements.isEmpty()) {
            return false;
        }
        elements.get(0).click();
        int nOfElements = elements.size();
        for (int i = 1; i < nOfElements; i++) {
            elements = getDriver().findElements(
                    By.xpath("//table[@id='commentsTable']//tbody//tr[contains(td[1],'" + userName + "')]/td[5]/form/input[@type='checkbox']"));
            elements.get(i).click();
        }

        waitForPageToLoad();
        elements = getDriver().findElements(
                By.xpath("//table[@id='commentsTable']//tbody//tr[contains(td[1],'" + userName + "')]/td[5]/form/input[@type='checkbox'  and @checked='checked']"));
        waitForPageToLoad();

        return !elements.isEmpty();
    }

    private WebElement getElemById(String element) {
        return getDriver().findElement(By.id(element));
    }

    public HomePageObject logOut() {
        WebElement logoutElement = getDriver().findElement(By.id("layout_login_form:logOutBtn"));
        logoutElement.click();
        waitForPageToLoad();

        return new HomePageObject(getDriver());
    }

    public HomePageObject goHome() {
        WebElement logoutElement = getDriver().findElement(By.id("homeLink"));
        logoutElement.click();
        waitForPageToLoad();

        return new HomePageObject(getDriver());
    }
}
