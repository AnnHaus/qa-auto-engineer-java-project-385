package io.hexlet.tests;

import io.hexlet.pages.LoginPage;
import io.hexlet.pages.LabelsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LabelCrudTest extends BaseUiTest {
    private LoginPage loginPage;
    private LabelsPage labelsPage;

    @BeforeEach
    public void loginBeforeTest() {
        loginPage = new LoginPage(driver);
        labelsPage = new LabelsPage(driver);

        loginPage.login("admin", "password");
        labelsPage.goToLabelsSection();
    }

    @Test
    public void testFullLabelCrudLifecycle() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String labelName = "Bug_" + timestamp;
        String updatedName = "Feature_" + timestamp;

        labelsPage.clickCreateLabel();
        labelsPage.fillForm(labelName);
        labelsPage.clickSave();

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        assertTrue(labelsPage.isLabelPresentInList(labelName), "Новая метка не появилась в списке таблицы!");

        labelsPage.clickEditLabel(labelName);
        labelsPage.fillForm(updatedName);
        labelsPage.clickSave();

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        assertTrue(labelsPage.isLabelPresentInList(updatedName), "Обновленное название метки не сохранилось в таблице!");

        labelsPage.clickEditLabel(updatedName);
        labelsPage.deleteLabelViaForm();

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        assertFalse(labelsPage.isLabelPresentInList(updatedName), "Метка всё ещё присутствует в списке после удаления!");
    }
}