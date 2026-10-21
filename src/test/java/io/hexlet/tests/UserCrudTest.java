package io.hexlet.tests;

import io.hexlet.pages.LoginPage;
import io.hexlet.pages.UsersPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserCrudTest extends BaseUiTest {
    private LoginPage loginPage;
    private UsersPage usersPage;

    @BeforeEach
    public void loginBeforeTest() {
        loginPage = new LoginPage(driver);
        usersPage = new UsersPage(driver);

        loginPage.login("admin", "password");
        usersPage.goToUsersSection();
    }

    @Test
    public void testUserEmailValidation() {
        String incorrectEmail = "bad-email-format";

        usersPage.clickCreateUser();
        usersPage.fillForm(incorrectEmail, "Test", "User");
        usersPage.clickSave();

        assertNotEquals("", usersPage.getEmailValidationError(), "Валидация некорректного email формата не сработала!");
    }

    @Test
    public void testFullUserCrudLifecycle() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String testEmail = "engineer" + timestamp + "@hexlet.io";
        String uniqueFirstName = "Anna" + timestamp;
        String updatedFirstName = "Alex" + timestamp;

        usersPage.clickCreateUser();
        usersPage.fillForm(testEmail, uniqueFirstName, "Dream");
        usersPage.clickSave();

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        assertTrue(usersPage.isUserPresentInList(testEmail), "Новый пользователь не появился в списке таблицы!");

        usersPage.clickEditUser(testEmail);
        usersPage.fillForm(testEmail, updatedFirstName, "Dream");
        usersPage.clickSave();

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        assertTrue(usersPage.isUserPresentInList(testEmail), "Пользователь исчез из списка после редактирования!");

        usersPage.clickEditUser(testEmail);
        usersPage.deleteUserViaForm();

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        assertFalse(usersPage.isUserPresentInList(testEmail), "Пользователь всё ещё присутствует в списке после удаления!");

        usersPage.massDeleteAllUsers();
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        assertFalse(usersPage.isUserPresentInList(testEmail), "Список пользователей не очистился после массового удаления!");
    }
}
