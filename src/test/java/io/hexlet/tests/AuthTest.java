package io.hexlet.tests;

import io.hexlet.pages.LoginPage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AuthTest extends BaseUiTest {

    @Test
    public void testSuccessfulLoginAndLogout() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("admin", "password");
        assertTrue(loginPage.isLoggedIn(), "Пользователь не смог авторизоваться в системе!");

        loginPage.logout();
        assertFalse(loginPage.isLoggedIn(), "Пользователь остался авторизован после нажатия Logout!");
    }
}