package io.hexlet.tests;

import io.hexlet.pages.LoginPage;
import io.hexlet.pages.LabelsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LabelCrudTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private LabelsPage labelsPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:5173/#/");

        loginPage = new LoginPage(driver);
        labelsPage = new LabelsPage(driver);

        loginPage.login("admin", "password");
    }

    @Test
    public void testCreateLabel() {
        String labelName = "Bug";
        labelsPage.goToLabelsSection();
        labelsPage.clickCreateLabel();
        labelsPage.createLabel(labelName);
        assertTrue(labelsPage.isLabelPresentInList(labelName),
                "Метка '" + labelName + "' не найдена в таблице после сохранения!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}