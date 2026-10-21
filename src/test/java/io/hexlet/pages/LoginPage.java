package io.hexlet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By usernameField = By.cssSelector("input[name='username']");
    private final By passwordField = By.cssSelector("input[name='password']");
    private final By signInButton = By.cssSelector("button[type='submit']");
    private final By dashboardMenuButton = By.xpath("//*[contains(text(), 'Dashboard')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
    }

    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignIn();
    }

    public boolean isWelcomeHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardMenuButton)).isDisplayed();
    }
}

