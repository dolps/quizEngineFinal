package com.woact.dolplads.exam2016.frontend.pages;

import com.woact.dolplads.exam2016.backend.entity.Post;
import com.woact.dolplads.exam2016.backend.entity.User;
import lombok.extern.java.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.woact.dolplads.exam2016.frontend.testUtils.PageObject;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Created by dolplads on 01/10/16.
 */
@Log
public class HomePageObject extends PageObject {
    private final String title = "Home Page";

    public HomePageObject(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains(title);
    }

    public void toStartPage() {
        getDriver().get(getBaseUrl() + "/home.jsf");
        waitForPageToLoad();
    }

    public LoginPageObject toLogin() {
        WebElement loginElement = getDriver().findElement(By.id("layout_login_form:loginBtn"));
        loginElement.click();
        waitForPageToLoad();

        return new LoginPageObject(getDriver());
    }

    public boolean isLoggedIn(String userName) {
        return getDriver().findElements(By.id("layout_login_form:loginBtn")).isEmpty()
                && getDriver().findElement(By.id("layout_login_form:welcomeMessage")).getText().contains(userName);
    }

    public HomePageObject logOut() {
        WebElement logoutElement = getDriver().findElement(By.id("layout_login_form:logOutBtn"));
        logoutElement.click();
        waitForPageToLoad();

        return new HomePageObject(getDriver());
    }

    public boolean isLoggedOut() {
        return getDriver().findElements(By.id("layout_login_form:logoutBtn")).isEmpty()
                && getDriver().findElements(By.id("layout_login_form:welcomeMessage")).isEmpty();
    }

    public int getNumberOfNews(String userName) {

        List<WebElement> elements = getDriver().findElements(
                By.xpath("//table[@id='eventsCreated']//tbody//tr[contains(td[2], '" + userName + "')]")
        );///td[5]
        return elements.size();
    }

    public void createNews(String text) {
        String form = "createPostForm:";
        getElement(form + "postText").sendKeys(text);
        getElement(form + "save").click();
        waitForPageToLoad();
    }


    public UserDetailsPageObject toUserDetails(String userName) {
        List<WebElement> elements = getDriver().findElements(
                By.xpath("//table[@id='eventsCreated']//tbody//tr[contains(td[2], '" + userName + "')]")
        );
        elements.get(0).click();
        waitForPageToLoad();

        return new UserDetailsPageObject(getDriver());
    }

    public void sortNewsBy(String sortOpt) {
        new Select(getDriver().findElement(By.id("sortForm:option"))).selectByVisibleText(sortOpt);
    }


    public boolean voteForNews(String userName) { //  and @checked='checked'
        List<WebElement> elements = getDriver().findElements(
                By.xpath("//table[@id='eventsCreated']//tbody//tr[contains(td[2], '" + userName + "')]/td[5]/form/table/tbody/tr/td/input[@type='radio']"));

        if (elements.isEmpty()) {
            return false;
        }
        elements.get(0).click();
        waitForPageToLoad();


        elements = getDriver().findElements(
                By.xpath("//table[@id='eventsCreated']//tbody//tr[contains(td[2], '" + userName + "')]/td[5]/form/table/tbody/tr/td/input[@type='radio' and @checked='checked']"));

        waitForPageToLoad();
        return !elements.isEmpty();
    }

    public boolean voteForFirstPost(int voteValue) {
        int val;
        if (voteValue == 1)
            val = 1;
        else if (voteValue == 0)
            val = 2;
        else val = 3;

        List<WebElement> elements = getDriver().findElements(
                By.xpath("//*[@id=\"eventsCreated\"]/tbody/tr[1]/td[5]/form/table/tbody/tr/td[" + val + "]/input[@type='radio']"));

        if (elements.isEmpty()) {
            return false;
        }
        elements.get(0).click();
        waitForPageToLoad();
        return true;
    }

    public int getScoreForFirstPost() {
        List<WebElement> elements = getDriver().findElements(
                By.xpath("//*[@id=\"eventsCreated\"]/tbody/tr[1]/td[4]")
        );

        if (!elements.isEmpty()) {
            String val = elements.get(0).getText();
            return Integer.parseInt(val);
        }
        return 0;
    }

    public boolean checkifTrimmed() {
        List<WebElement> elements = getDriver().findElements(
                By.xpath("//*[@id=\"eventsCreated\"]/tbody/tr[1]/td[3]")
        );
        if (!elements.isEmpty()) {
            String val = elements.get(0).getText();
            return val.length() == 29 && val.endsWith("...");
        }

        return false;
    }


    public int numberOfEventsOnHomePage() {
        int rowCount = getDriver().findElements(By.xpath("//table[@id='eventsCreated']/tbody/tr")).size();
        log.log(Level.INFO, "number of events: " + rowCount);
        return rowCount;
    }

    private WebElement getElement(String element) {
        return getDriver().findElement(By.id(element));
    }


    public void clickCountryCheckbox() {
        WebElement e = getDriver().findElement(By.id("countryForm:showUserCountry"));

        e.click();

        waitForPageToLoad();
    }

    public void setCountryCheckboxTo(boolean value) {
        WebElement e = getDriver().findElement(By.id("countryForm:showUserCountry"));
        boolean isChecked = e.isSelected();

        if (value != isChecked) {
            clickCountryCheckbox();
        }
    }

    public void setAttendance(String eventName, boolean value) {
        boolean attending = isAttending(eventName);
        if (attending == value) return;

        log.log(Level.INFO, "trying to set attendance");
        List<WebElement> elements = getDriver().findElements(
                By.xpath("//table[@id='eventsCreated']//tbody//tr[contains(td[2], '" + eventName + "')]/td[6]/form/input[@type='checkbox']")
        );

        if (!elements.isEmpty()) {
            elements.get(0).click();
            waitForPageToLoad();
        }
    }

    public boolean isAttending(String eventName) {
        List<WebElement> elements = getDriver().findElements(
                By.xpath("//table[@id='eventsCreated']//tbody//tr[contains(td[2], '" + eventName + "')]/td[6]/form/input[@type='checkbox' and @checked='checked']")
        );

        return !elements.isEmpty();
    }

    public int getNumberOfAttendees(String eventName) {
        List<WebElement> elements = getDriver().findElements(
                By.xpath("//table[@id='eventsCreated']//tbody//tr[contains(td[2], '" + eventName + "')]/td[5]")
        );
        if (elements.isEmpty()) {
            return -1;
        }
        return Integer.parseInt(elements.get(0).getText());
    }

    public boolean isSortedByScore() {
        List<WebElement> elements = getDriver().findElements(
                By.xpath("//table[@id='eventsCreated']//tbody//tr/td[4]"));

        List<String> list = elements.stream().map(WebElement::getText).collect(Collectors.toList());
        List<String> sortedList = list.stream().sorted((l1, l2) -> l2.compareTo(l1)).collect(Collectors.toList());

        return list.equals(sortedList);
    }
}
