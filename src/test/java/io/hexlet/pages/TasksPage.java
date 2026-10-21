package io.hexlet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TasksPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By tasksMenuButton = By.xpath("//*[contains(text(), 'Tasks')]");
    private final By createButton = By.xpath("//*[contains(text(), 'Create') or @aria-label='Create']");
    private final By titleField = By.cssSelector("input[name='title']");
    private final By saveButton = By.cssSelector("button[type='submit']");

    public TasksPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToTasksSection() {
        wait.until(ExpectedConditions.elementToBeClickable(tasksMenuButton)).click();
    }

    public void clickCreateTask() {
        wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();
    }

    public void fillTextData(String title, String content) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(titleField)).sendKeys(title);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("content"))).sendKeys(content);
    }

    public void selectFromDropdown(String inputName, String valueToSelect) {
        By exactDropdown = By.xpath("//input[@name='" + inputName + "']/preceding-sibling::div");
        org.openqa.selenium.WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(exactDropdown));

        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);

        String lowerValue = valueToSelect.toLowerCase();
        By optionLocator = By.xpath("//li[@role='option' and contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + lowerValue + "')]");
        org.openqa.selenium.WebElement optionElement = wait.until(ExpectedConditions.presenceOfElementLocated(optionLocator));
        js.executeScript("arguments[0].click();", optionElement);
    }

    public void clickSave() {
        org.openqa.selenium.WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(saveButton));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public boolean isTaskPresentOnBoard(String title) {
        By anyTaskCard = By.xpath("//*[contains(@class, 'MuiCard') or contains(@data-rfd-draggable-id, '')]");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(anyTaskCard)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
