package io.hexlet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class TasksPage extends BasePage {

    private final By titleField = By.name("title");
    private final By contentField = By.name("content");
    private final By saveButton = By.xpath("//button[@type='submit' or contains(text(), 'SAVE') or contains(text(), 'Save') or contains(text(), 'Создать') or contains(text(), 'Сохранить')]");
    private final By taskCards = By.xpath("//*[contains(text(), 'EDIT') or contains(text(), 'Edit') or contains(text(), 'Редактировать')]");
    private final By createButtonOnBoard = By.xpath("//a[contains(@href, '/create') or contains(text(), 'CREATE') or contains(text(), 'Создать')]");

    public TasksPage(WebDriver driver) {
        super(driver);
    }

    public void clickNavigateToCreateForm() {
        WebElement createBtn = wait.until(ExpectedConditions.elementToBeClickable(createButtonOnBoard));
        createBtn.click();
    }

    public void fillTextData(String title, String content) {
        WebElement titleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(titleField));
        titleInput.click();
        titleInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        titleInput.clear();
        titleInput.sendKeys(title);

        WebElement contentInput = wait.until(ExpectedConditions.visibilityOfElementLocated(contentField));
        contentInput.click();
        contentInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        contentInput.clear();
        contentInput.sendKeys(content);
    }

    public void selectRequiredDropdowns() {
        try {
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;

            js.executeScript(
                    "var inputs = document.querySelectorAll('form input[type=\"hidden\"], form input[name*=\"Id\"], form input[name*=\"status\"], form select');" +
                            "inputs.forEach(function(input) {" +
                            "    input.value = '1';" +
                            "    if(input.tagName === 'SELECT') { input.selectedIndex = 1; }" +
                            "    input.dispatchEvent(new Event('input', { bubbles: true }));" +
                            "    input.dispatchEvent(new Event('change', { bubbles: true }));" +
                            "});"
            );
            Thread.sleep(800);
            System.out.println("Лог: Успешно форсировали выпадающие поля на форме создания.");
        } catch (Exception e) {
            System.out.println("Лог: Ошибка при заполнении дропдаунов: " + e.getMessage());
        }
    }

    public void selectDropdownsForEditForm() {
        try {
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;

            js.executeScript(
                    "var inputs = document.querySelectorAll('form input[type=\"hidden\"], form input[name*=\"Id\"], form input[name*=\"status\"], form select');" +
                            "inputs.forEach(function(input) {" +
                            "    var currentVal = input.value || input.defaultValue || input.getAttribute('value');" +
                            "    if (!currentVal || currentVal === '0' || currentVal === '') {" +
                            "        currentVal = '1';" +
                            "    }" +
                            "    input.value = currentVal;" +
                            "    if(input.tagName === 'SELECT') { input.selectedIndex = 1; }" +
                            "    input.dispatchEvent(new Event('input', { bubbles: true }));" +
                            "    input.dispatchEvent(new Event('change', { bubbles: true }));" +
                            "});"
            );
            Thread.sleep(800);
            System.out.println("Лог: Успешно синхронизировали выпадающие поля на форме редактирования.");
        } catch (Exception e) {
            System.out.println("Лог: Ошибка при синхронизации полей редактирования: " + e.getMessage());
        }
    }

    public void clickSaveButton() {
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveBtn.click();
    }

    public boolean isTaskPresentOnBoard(String title) {
        long endTime = System.currentTimeMillis() + 6000;
        while (System.currentTimeMillis() < endTime) {
            try {
                if (driver.getPageSource().contains(title)) {
                    return true;
                }
                Thread.sleep(400);
            } catch (Exception e) {
            }
        }
        return driver.getPageSource().contains(title);
    }

    public void applyStatusFilter() {
        try {
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;

            WebElement filterDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='button' or @role='combobox' or contains(@class, 'select')]")
            ));
            js.executeScript("arguments[0].click();", filterDropdown);
            Thread.sleep(600);

            js.executeScript(
                    "var options = document.querySelectorAll('li[role=\"option\"], .MuiMenuItem-root, [class*=\"option\"]');" +
                            "if (options.length > 0) { options[0].click(); }"
            );
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Лог: Не удалось применить фильтр: " + e.getMessage());
        }
    }

    public int getTaskCardsCount() {
        try {
            return driver.findElements(taskCards).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void openTaskByTitle(String title) {
        By editLinkLocator = By.xpath(
                "//*[contains(text(), '" + title + "')]/ancestor::tr//*[contains(text(), 'Edit') or contains(text(), 'EDIT')]" +
                        " | //*[contains(text(), '" + title + "')]/ancestor::div[contains(@class, 'card') or @role='row']//*[contains(text(), 'Edit') or contains(text(), 'EDIT')]" +
                        " | //*[contains(text(), '" + title + "')]/following::a[contains(text(), 'Edit') or contains(text(), 'EDIT')]" +
                        " | //*[contains(text(), '" + title + "')]/..//a"
        );
        WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(editLinkLocator));
        editBtn.click();
    }
}
