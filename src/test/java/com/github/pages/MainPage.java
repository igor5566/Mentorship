package com.github.pages;

import com.github.base.BasePage;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {
    String s = "";

    @FindBy(xpath = "//a[contains(text(), 'Pull requests')]")
    private WebElement pullRequestBtn;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardVisible() {
        try {
            isElementVisible(pullRequestBtn, 10);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
