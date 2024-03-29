package com.github.pages;

import com.github.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static github.core.ConfigManager.*;

public class CreateRepoPage extends BasePage {

    @FindBy(xpath = "//input[contains(@class, 'form-control js-repo-name')]")
    private WebElement repoNameField;

    @FindBy(xpath = "//button[@class='btn btn-primary first-in-line']")
    private WebElement createBtn;

    public CreateRepoPage(WebDriver driver) {
        super(driver);
    }

    public NewRepoPage createNewRepoByUI(String name) {
        type(repoNameField, name);
        waitUntilPageIsReady(timesWait);
        scrollPageDown();
        click(createBtn);
        return new NewRepoPage(driver);
    }
}
