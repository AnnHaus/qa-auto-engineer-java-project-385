package io.hexlet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class UsersPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By usersMenuButton = By.xpath("//*[contains(text(), 'Users')]");
    private final By createButton = By.xpath("//*[contains(text(), 'Create') or @aria-label='Create']");
    private final By emailField = By.cssSelector("input[name='email']");
    private final By firstNameField = By.cssSelector("input[name='firstName']");
    private final By lastNameField = By.cssSelector("input[name='lastName']");
    private final By saveButton = By.cssSelector("button[type='submit']");

    public UsersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void goToUsersSection() {
        wait.until(ExpectedConditions.elementToBeClickable(usersMenuButton)).click();
    }
    public void clickCreateUser() {
        wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();
    }
    public void createUser(String email, String firstName, String lastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(saveButton).click();
    }
    public boolean isUserPresentInList(String email) {
        By userEmailInTable = By.xpath("//*[contains(text(), '" + email + "')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(userEmailInTable)).isDisplayed();
    }
}