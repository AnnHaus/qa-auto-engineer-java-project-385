package io.hexlet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
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
        titleInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        titleInput.sendKeys(Keys.BACK_SPACE);
        titleInput.clear();
        titleInput.sendKeys(title);

        WebElement contentInput = wait.until(ExpectedConditions.visibilityOfElementLocated(contentField));
        contentInput.click();
        contentInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        contentInput.sendKeys(Keys.BACK_SPACE);
        contentInput.clear();
        contentInput.sendKeys(content);
    }

    public void selectRequiredDropdowns() {
        try {
            Actions actions = new Actions(driver);
            List<WebElement> dropdowns = driver.findElements(By.xpath("//div[@role='combobox' or @role='button' or contains(@class, 'select')]"));

            if (dropdowns.size() >= 1) {
                dropdowns.get(0).click();
                Thread.sleep(800);
                actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
                Thread.sleep(600);
            }

            if (dropdowns.size() >= 2) {
                dropdowns.get(1).click();
                Thread.sleep(800);
                actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
                Thread.sleep(600);
            }
        } catch (Exception e) {
            System.out.println("Лог: Предупреждение при выборе полей: " + e.getMessage());
        }
    }

    public void selectDropdownsForEditForm() {
        try {
            driver.switchTo().activeElement().sendKeys(Keys.TAB);
            Thread.sleep(500);
            driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
            Thread.sleep(300);
            driver.switchTo().activeElement().sendKeys(Keys.ENTER);
            Thread.sleep(500);

            driver.switchTo().activeElement().sendKeys(Keys.TAB);
            Thread.sleep(500);
            driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
            Thread.sleep(300);
            driver.switchTo().activeElement().sendKeys(Keys.ENTER);
            Thread.sleep(500);

            System.out.println("Лог: Успешно заполнили поля формы редактирования через нативную TAB-последовательность.");
        } catch (Exception e) {
            System.out.println("Лог: Предупреждение при клавиатурном выборе полей: " + e.getMessage());
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
            Thread.sleep(800);

            Actions actions = new Actions(driver);
            actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
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
