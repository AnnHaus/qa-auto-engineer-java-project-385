package io.hexlet.tests;

import io.hexlet.pages.LoginPage;
import io.hexlet.pages.TaskStatusesPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskStatusCrudTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private TaskStatusesPage taskStatusesPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:5173/#/");

        loginPage = new LoginPage(driver);
        taskStatusesPage = new TaskStatusesPage(driver);

        loginPage.login("admin", "password");
    }

    @Test
    public void testCreateTaskStatus() {
        String statusName = "In Progress";
        String statusSlug = "in-progress";

        taskStatusesPage.goToTaskStatusesSection();
        taskStatusesPage.clickCreateStatus();
        taskStatusesPage.createStatus(statusName, statusSlug);
        assertTrue(taskStatusesPage.isStatusPresentInList(statusName),
                "Статус с названием '" + statusName + "' не найден в таблице после сохранения!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}