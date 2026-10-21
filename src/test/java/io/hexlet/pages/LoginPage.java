package io.hexlet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private final By usernameField = By.cssSelector("input[name='username']");
    private final By passwordField = By.cssSelector("input[name='password']");
    private final By signInButton = By.cssSelector("button[type='submit']");

    private final By profileButton = By.xpath("//button[@aria-label='Profile']");

    private final By logoutMenuButton = By.xpath("//*[contains(@role, 'menuitem') or contains(@class, 'Menu')]//*[contains(text(), 'Logout') or contains(text(), 'Sign out')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(profileButton)).click();

        org.openqa.selenium.WebElement logoutBtn = wait.until(ExpectedConditions.presenceOfElementLocated(logoutMenuButton));

        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", logoutBtn);
    }

    public boolean isLoggedIn() {
        try {
            org.openqa.selenium.support.ui.WebDriverWait shortWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(2));

            if (shortWait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).isDisplayed()) {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
        return true;
    }
}