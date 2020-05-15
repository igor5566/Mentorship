package com.github.pages;

import com.github.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static core.utils.MavenUtils.*;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@name='login']")
    private WebElement loginField;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//input[@name='commit']")
    private WebElement singInBtn;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Login into account.")
    public MainPage logIn(String email, String password) {
        waitUntilPageIsReady(timesWait);
        type(loginField, email);
        waitUntilPageIsReady(timesWait);
        type(passwordField, password);
        click(singInBtn);
        return new MainPage(driver);
    }
}
