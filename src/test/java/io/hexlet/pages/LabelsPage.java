package io.hexlet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LabelsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By labelsMenuButton = By.xpath("//*[contains(text(), 'Labels')]");
    private final By createButton = By.xpath("//*[contains(text(), 'Create') or @aria-label='Create']");

    private final By nameField = By.cssSelector("input[name='name']");
    private final By saveButton = By.cssSelector("button[type='submit']");

    public LabelsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void goToLabelsSection() {
        wait.until(ExpectedConditions.elementToBeClickable(labelsMenuButton)).click();
    }
    public void clickCreateLabel() {
        wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();
    }
    public void createLabel(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        driver.findElement(saveButton).click();
    }
    public boolean isLabelPresentInList(String name) {
        By labelNameInTable = By.xpath("//*[contains(text(), '" + name + "')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(labelNameInTable)).isDisplayed();
    }
}