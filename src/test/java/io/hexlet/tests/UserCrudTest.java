package io.hexlet.tests;

import io.hexlet.pages.LoginPage;
import io.hexlet.pages.UsersPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class UserCrudTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private UsersPage usersPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:5173/#/");

        loginPage = new LoginPage(driver);
        usersPage = new UsersPage(driver);
        loginPage.login("admin", "password");
    }

    @Test
    public void testCreateUser() {
        String email = "testuser@hexlet.io";
        usersPage.goToUsersSection();
        usersPage.clickCreateUser();
        usersPage.createUser(email, "Ivan", "Ivanov");

        org.junit.jupiter.api.Assertions.assertTrue(
                usersPage.isUserPresentInList(email),
                "Пользователь с email " + email + " не найден в списке после создания!"
        );
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}