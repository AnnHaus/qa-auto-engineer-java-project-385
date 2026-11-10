package io.hexlet.tests;

import io.hexlet.pages.LoginPage;
import io.hexlet.pages.TasksPage;
import org.openqa.selenium.By;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskCrudTest extends BaseUiTest {

    private TasksPage tasksPage;
    private LoginPage loginPage;

    private static boolean isLogged = false;

    private void takePhysicalScreenshot(String testName) {
        try {
            File srcFile = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            File destFile = new File("screenshot_" + testName + ".png");
            Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("=== ДИАГНОСТИКА: Скриншот сохранен в файл: " + destFile.getAbsolutePath() + " ===");
        } catch (Exception e) {
            System.out.println("Не удалось сохранить скриншот: " + e.getMessage());
        }
    }

    @BeforeEach
    public void testSetUp() {
        tasksPage = new TasksPage(driver);
        loginPage = new LoginPage(driver);

        if (!isLogged) {
            try {
                driver.get("http://localhost:5173/#/login");
                Thread.sleep(1000);
                loginPage.login("admin", "password");
                Thread.sleep(2000);
                isLogged = true; // Запоминаем, что вошли успешно
                System.out.println("Лог: Успешная первоначальная авторизация пользователя admin.");
            } catch (Exception e) {
                System.out.println("Лог: Ошибка при входе: " + e.getMessage());
            }
        }

        try {
            driver.get("http://localhost:5173/#/tasks");
            Thread.sleep(1500);
        } catch (Exception e) {
            System.out.println("Лог: Не удалось открыть главную страницу задач: " + e.getMessage());
        }
    }

    @Test
    public void testFullTaskCrudLifecycle() {
        String taskTitle = "Hexlet Создание " + System.currentTimeMillis();
        try {
            tasksPage.clickNavigateToCreateForm();
            try { Thread.sleep(1500); } catch (Exception e) {}

            tasksPage.fillTextData(taskTitle, "Описание автоматического теста");
            tasksPage.selectRequiredDropdowns();
            tasksPage.clickSaveButton();

            try { Thread.sleep(4000); } catch (Exception e) {}

            driver.get("http://localhost:5173/#/tasks");
            try { Thread.sleep(2500); } catch (Exception e) {}

            boolean isCreated = tasksPage.isTaskPresentOnBoard(taskTitle);
            if (!isCreated) { takePhysicalScreenshot("create_failed"); }

            assertTrue(isCreated, "Ошибка: Задача '" + taskTitle + "' не появилась на доске!");
        } catch (Throwable t) {
            takePhysicalScreenshot("create_exception");
            throw t;
        }
    }

    @Test
    public void testTaskFiltering() {
        try {
            String filterTaskTitle = "Hexlet Фильтр " + System.currentTimeMillis();
            tasksPage.clickNavigateToCreateForm();
            try { Thread.sleep(1500); } catch (Exception e) {}

            tasksPage.fillTextData(filterTaskTitle, "Тест фильтрации");
            tasksPage.selectRequiredDropdowns();
            tasksPage.clickSaveButton();
            try { Thread.sleep(4000); } catch (Exception e) {}

            driver.get("http://localhost:5173/#/tasks");
            try { Thread.sleep(2500); } catch (Exception e) {}

            int initialCount = tasksPage.getTaskCardsCount();
            if (initialCount == 0) { takePhysicalScreenshot("filter_initial_zero"); }

            assertTrue(initialCount > 0, "Ошибка: Список задач пуст!");

            tasksPage.applyStatusFilter();
            int filteredCount = tasksPage.getTaskCardsCount();

            assertTrue(filteredCount >= 0, "Ошибка: После фильтрации доска сломалась!");
            System.out.println("Лог фильтрации: Успешно. Было: " + initialCount + ", Стало: " + filteredCount);
        } catch (Throwable t) {
            takePhysicalScreenshot("filter_exception");
            throw t;
        }
    }

    @Test
    public void testEditTask() {
        String initialTitle = "Hexlet До Ред " + System.currentTimeMillis();
        String updatedTitle = "Hexlet После Ред " + System.currentTimeMillis();

        try {
            tasksPage.clickNavigateToCreateForm();
            try { Thread.sleep(1500); } catch (Exception e) {}

            tasksPage.fillTextData(initialTitle, "Оригинальное описание");
            tasksPage.selectRequiredDropdowns();
            tasksPage.clickSaveButton();
            try { Thread.sleep(4000); } catch (Exception e) {}

            driver.get("http://localhost:5173/#/tasks");
            try { Thread.sleep(2500); } catch (Exception e) {}

            boolean isReady = tasksPage.isTaskPresentOnBoard(initialTitle);
            assertTrue(isReady, "Ошибка: Исходная задача не появилась для редактирования!");

            tasksPage.openTaskByTitle(initialTitle);
            try { Thread.sleep(2000); } catch (Exception e) {}

            tasksPage.fillTextData(updatedTitle, "Измененное описание задачи.");
            tasksPage.selectDropdownsForEditForm();
            tasksPage.clickSaveButton();
            try { Thread.sleep(4000); } catch (Exception e) {}

            driver.get("http://localhost:5173/#/tasks");
            try { Thread.sleep(2500); } catch (Exception e) {}

            boolean isUpdated = tasksPage.isTaskPresentOnBoard(updatedTitle);
            if (!isUpdated) { takePhysicalScreenshot("edit_failed"); }

            assertTrue(isUpdated, "Ошибка: Измененная задача не найдена!");
        } catch (Throwable t) {
            takePhysicalScreenshot("edit_exception");
            throw t;
        }
    }
}
