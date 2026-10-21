package io.hexlet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LabelsPage extends BasePage {

    private final By labelsMenuButton = By.xpath("//a[contains(@href, 'labels') or contains(@href, 'Label')]");
    private final By createButton = By.xpath("//*[contains(text(), 'Create') or @aria-label='Create']");

    private final By nameField = By.cssSelector("input[name='name']");
    private final By saveButton = By.cssSelector("button[type='submit']");

    public LabelsPage(WebDriver driver) {
        super(driver);
    }

    public void goToLabelsSection() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(labelsMenuButton));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void clickCreateLabel() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(createButton));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void fillForm(String name) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(name);
    }

    public void clickSave() {
        WebElement saveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(saveButton));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", saveBtn);
    }

    public boolean isLabelPresentInList(String name) {
        By cell = By.xpath("//*[contains(text(), '" + name + "')]");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(cell)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickEditLabel(String name) {
        By rowCell = By.xpath("//*[contains(text(), '" + name + "')]");
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(rowCell));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void deleteLabelViaForm() {
        By deleteButton = By.xpath("//button[contains(@class, 'ra-delete-button') or contains(text(), 'Delete')]");
        WebElement delBtn = wait.until(ExpectedConditions.presenceOfElementLocated(deleteButton));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", delBtn);
    }
}