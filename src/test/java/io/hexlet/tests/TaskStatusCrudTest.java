package io.hexlet.tests;

import io.hexlet.pages.LoginPage;
import io.hexlet.pages.StatusesPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TaskStatusCrudTest extends BaseUiTest {
    private LoginPage loginPage;
    private StatusesPage statusesPage;

    @BeforeEach
    public void loginBeforeTest() {
        loginPage = new LoginPage(driver);
        statusesPage = new StatusesPage(driver);

        loginPage.login("admin", "password");
        statusesPage.goToStatusesSection();
    }

    @Test
    public void testFullStatusCrudLifecycle() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String statusName = "Column_" + timestamp;
        String statusSlug = "slug_" + timestamp;
        String updatedName = "Updated_" + timestamp;

        statusesPage.clickCreateStatus();
        statusesPage.fillForm(statusName, statusSlug);
        statusesPage.clickSave();

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        assertTrue(statusesPage.isStatusPresentInList(statusName), "Новый статус не появился в списке таблицы!");

        statusesPage.clickEditStatus(statusName);
        statusesPage.fillForm(updatedName, statusSlug);
        statusesPage.clickSave();

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        assertTrue(statusesPage.isStatusPresentInList(updatedName), "Обновленное имя статуса не сохранился в таблице!");

        statusesPage.clickEditStatus(updatedName);
        statusesPage.deleteStatusViaForm();

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        assertFalse(statusesPage.isStatusPresentInList(updatedName), "Статус всё ещё присутствует в списке после удаления!");

        statusesPage.massDeleteAllStatuses();
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        assertFalse(statusesPage.isStatusPresentInList(statusName), "Список статусов не очистился после массового удаления!");
    }
}