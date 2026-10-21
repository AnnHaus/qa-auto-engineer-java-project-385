package io.hexlet.tests;

import io.hexlet.pages.LoginPage;
import io.hexlet.pages.TasksPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskCrudTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private TasksPage tasksPage;

    @BeforeEach
    public void setUp() {
        org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.get("http://localhost:5173/#/");

        loginPage = new LoginPage(driver);
        tasksPage = new TasksPage(driver);

        loginPage.login("admin", "password");
    }

    @Test
    public void testCreateTask() {
        String taskTitle = "Task" + System.currentTimeMillis();
        String taskContent = "Automated test content";

        tasksPage.goToTasksSection();
        tasksPage.clickCreateTask();

        tasksPage.fillTextData(taskTitle, taskContent);

        tasksPage.clickSave();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tasksPage.goToTasksSection();

        assertTrue(tasksPage.isTaskPresentOnBoard(taskTitle),
                "Задача '" + taskTitle + "' не появилась на канбан-доске!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
