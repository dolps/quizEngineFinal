package pages;

import lombok.extern.java.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testUtils.PageObject;

import java.util.List;
import java.util.logging.Level;

/**
 * Created by dolplads on 01/10/16.
 */
@Log
public class HomePageObject extends PageObject {
    private final String title = "Event List Home Page";

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

        log.log(Level.INFO,"trying to set attendance");
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
}
