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

    private final By menuOptions = By.xpath("//li[@role='option' or contains(@class, 'MenuItem') or contains(@class, 'option')]");

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

            List<WebElement> dropdowns = driver.findElements(By.xpath("//div[@role='combobox' or @role='button' or contains(@class, 'select')]"));
            if (!dropdowns.isEmpty()) {
                js.executeScript("arguments[0].click();", dropdowns.get(0));
                WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(menuOptions));
                js.executeScript("arguments[0].click();", firstOption);
                Thread.sleep(400);
            }

            dropdowns = driver.findElements(By.xpath("//div[@role='combobox' or @role='button' or contains(@class, 'select')]"));
            if (dropdowns.size() >= 2) {
                js.executeScript("arguments[0].click();", dropdowns.get(1));
                WebElement secondOption = wait.until(ExpectedConditions.elementToBeClickable(menuOptions));
                js.executeScript("arguments[0].click();", secondOption);
                Thread.sleep(400);
            }
        } catch (Exception e) {
            System.out.println("Лог: Предупреждение при выборе полей создания: " + e.getMessage());
        }
    }

    public void selectDropdownsForEditForm() {
        try {
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;

            List<WebElement> formDropdowns = driver.findElements(By.xpath("//form//div[@role='combobox' or @role='button' or contains(@class, 'select')]"));
            if (!formDropdowns.isEmpty()) {
                js.executeScript("arguments[0].click();", formDropdowns.get(0));
                WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(menuOptions));
                js.executeScript("arguments[0].click();", firstOption);
                Thread.sleep(400);
            }

            formDropdowns = driver.findElements(By.xpath("//form//div[@role='combobox' or @role='button' or contains(@class, 'select')]"));
            if (formDropdowns.size() >= 2) {
                js.executeScript("arguments[0].click();", formDropdowns.get(1));
                WebElement secondOption = wait.until(ExpectedConditions.elementToBeClickable(menuOptions));
                js.executeScript("arguments[0].click();", secondOption);
                Thread.sleep(400);
            }
        } catch (Exception e) {
            System.out.println("Лог: Предупреждение при выборе полей редактирования: " + e.getMessage());
        }
    }

    public void clickSaveButton() {
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveBtn.click();
    }

    public boolean isTaskPresentOnBoard(String title) {
        try {
            By taskLocator = By.xpath("//*[contains(text(), '" + title + "')]");
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(taskLocator));
            return element != null;
        } catch (Exception e) {
            return false;
        }
    }

    public void applyStatusFilter() {
        try {
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            WebElement filterDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='button' or @role='combobox' or contains(@class, 'select')]")
            ));
            js.executeScript("arguments.click();", filterDropdown);

            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(menuOptions));
            js.executeScript("arguments[0].click();", option);
            Thread.sleep(1500);
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
