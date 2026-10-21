package io.hexlet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StatusesPage extends BasePage {

    private final By statusesMenuButton = By.xpath("//a[contains(@href, 'status') or contains(@href, 'Status')]");
    private final By createButton = By.xpath("//*[contains(text(), 'Create') or @aria-label='Create']");

    private final By nameField = By.cssSelector("input[name='name']");
    private final By slugField = By.cssSelector("input[name='slug']");
    private final By saveButton = By.cssSelector("button[type='submit']");

    private final By selectAllCheckbox = By.cssSelector("th.MuiTableCell-paddingCheckbox input[type='checkbox']");
    private final By massDeleteButton = By.xpath("//*[contains(text(), 'Delete') or @aria-label='Delete']");

    public StatusesPage(WebDriver driver) {
        super(driver);
    }

    public void goToStatusesSection() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(statusesMenuButton));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void clickCreateStatus() {
        wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();
    }

    private void clearAndType(By locator, String text) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(text);
    }

    public void fillForm(String name, String slug) {
        clearAndType(nameField, name);
        clearAndType(slugField, slug);
    }

    public void clickSave() {
        WebElement saveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(saveButton));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", saveBtn);
    }

    public boolean isStatusPresentInList(String textToFind) {
        By cell = By.xpath("//*[contains(text(), '" + textToFind + "')]");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(cell)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickEditStatus(String uniqueText) {
        By rowCell = By.xpath("//*[contains(text(), '" + uniqueText + "')]");
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(rowCell));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void deleteStatusViaForm() {
        By deleteButton = By.xpath("//button[contains(@class, 'ra-delete-button') or contains(text(), 'Delete')]");
        WebElement delBtn = wait.until(ExpectedConditions.presenceOfElementLocated(deleteButton));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", delBtn);
    }

    public void massDeleteAllStatuses() {
        WebElement selectAll = wait.until(ExpectedConditions.presenceOfElementLocated(selectAllCheckbox));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", selectAll);

        WebElement massDel = wait.until(ExpectedConditions.presenceOfElementLocated(massDeleteButton));
        js.executeScript("arguments[0].click();", massDel);
    }
}
