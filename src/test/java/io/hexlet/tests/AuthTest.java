package io.hexlet.tests;

import io.hexlet.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:5173/#/");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testSuccessfulLogin() {
        loginPage.login("admin", "password");

        assertTrue(loginPage.isWelcomeHeaderDisplayed(),
                "Заголовок приветствия не отобразился! Возможно, авторизация завершилась ошибкой.");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}