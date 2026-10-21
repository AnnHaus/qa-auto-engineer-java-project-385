package io.hexlet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UsersPage extends BasePage {

    private final By usersMenuButton = By.xpath("//*[contains(text(), 'Users')]");
    private final By createButton = By.xpath("//*[contains(text(), 'Create') or @aria-label='Create']");

    private final By emailField = By.cssSelector("input[name='email']");
    private final By firstNameField = By.cssSelector("input[name='firstName']");
    private final By lastNameField = By.cssSelector("input[name='lastName']");
    private final By saveButton = By.cssSelector("button[type='submit']");

    private final By emailHelperText = By.xpath("//p[contains(@class, 'Mui-error') or contains(@class, 'helper-text')]");
    private final By selectAllCheckbox = By.cssSelector("th.MuiTableCell-paddingCheckbox input[type='checkbox']");
    private final By massDeleteButton = By.xpath("//*[contains(text(), 'Delete') or @aria-label='Delete']");

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    public void goToUsersSection() {
        wait.until(ExpectedConditions.elementToBeClickable(usersMenuButton)).click();
    }

    public void clickCreateUser() {
        wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();
    }

    private void clearAndType(By locator, String text) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(text);
    }

    public void fillForm(String email, String firstName, String lastName) {
        clearAndType(emailField, email);
        clearAndType(firstNameField, firstName);
        clearAndType(lastNameField, lastName);
    }

    public void clickSave() {
        WebElement saveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(saveButton));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", saveBtn);
    }

    public boolean isUserPresentInList(String email) {
        By userCell = By.xpath("//*[contains(text(), '" + email + "')]");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(userCell)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickEditUser(String email) {
        By emailElement = By.xpath("//*[contains(text(), '" + email + "')]");

        org.openqa.selenium.WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(emailElement));

        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public String getEmailValidationError() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(emailHelperText)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void deleteUserViaForm() {
        By deleteButton = By.xpath("//button[contains(@class, 'ra-delete-button') or contains(text(), 'Delete')]");
        WebElement delBtn = wait.until(ExpectedConditions.presenceOfElementLocated(deleteButton));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", delBtn);
    }

    public void massDeleteAllUsers() {
        WebElement selectAll = wait.until(ExpectedConditions.presenceOfElementLocated(selectAllCheckbox));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", selectAll);

        WebElement massDel = wait.until(ExpectedConditions.presenceOfElementLocated(massDeleteButton));
        js.executeScript("arguments[0].click();", massDel);
    }
}
