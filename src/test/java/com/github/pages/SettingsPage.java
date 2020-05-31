package com.github.pages;

import com.github.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SettingsPage extends BasePage {

    @FindBy(xpath = "//div[@class='Box Box--danger']/ul/li[4]//summary")
    private WebElement deleteBtn;

    @FindBy(xpath = "//*[@aria-label='Delete repository']//input[@class='form-control input-block']")
    private WebElement deleteConfirmField;

    @FindBy(xpath = "//*[@aria-label='Delete repository']//form//button")
    private WebElement confirmBtn;

    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    public void deleteRepoByUI(String name) {
        scrollPageDown();
        click(deleteBtn);
        type(deleteConfirmField, name);
        click(confirmBtn);
    }
}
