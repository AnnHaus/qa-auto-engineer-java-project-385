package io.hexlet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TaskStatusesPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By statusesMenuButton = By.xpath("//*[contains(text(), 'Task statuses')]");
    private final By createButton = By.xpath("//*[contains(text(), 'Create') or @aria-label='Create']");
    private final By nameField = By.cssSelector("input[name='name']");
    private final By slugField = By.cssSelector("input[name='slug']");
    private final By saveButton = By.cssSelector("button[type='submit']");

    public TaskStatusesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void goToTaskStatusesSection() {
        wait.until(ExpectedConditions.elementToBeClickable(statusesMenuButton)).click();
    }
    public void clickCreateStatus() {
        wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();
    }
    public void createStatus(String name, String slug) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        driver.findElement(slugField).sendKeys(slug);
        driver.findElement(saveButton).click();
    }
    public boolean isStatusPresentInList(String name) {
        By statusNameInTable = By.xpath("//*[contains(text(), '" + name + "')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(statusNameInTable)).isDisplayed();
    }
}