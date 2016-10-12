package pages;

import com.woact.dolplads.entity.User;
import com.woact.dolplads.enums.CountryEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testUtils.PageObject;

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
                           String firstName, String middleName, String lastName, CountryEnum country) {
        String form = "createForm:";
        getElement(form + "userName").sendKeys(userName);
        getElement(form + "password").sendKeys(password);
        getElement(form + "confirmPassword").sendKeys(confirmPassword);
        getElement(form + "firstName").sendKeys(firstName);
        getElement(form + "middleName").sendKeys(middleName);
        getElement(form + "lastName").sendKeys(lastName);
        getElement(form + "save").click();

        // TODO: 12/10/2016 fix this just for compiling now
        return new User("", "", "username", null);
    }

    private WebElement getElement(String element) {
        return getDriver().findElement(By.id(element));
    }
}
