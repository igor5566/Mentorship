package com.github.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void click(WebElement element) {
        new WebDriverWait(driver, 10).until(elementToBeClickable(element)).click();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public void type(WebElement element, String content) {
        element.clear();
        element.sendKeys(content);
    }

    public void waitUntilPageIsReady(int maxWaitTime) {
        for (int j = 0; j < maxWaitTime; j++) {
            String pageLoad = ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("return document.readyState").toString();
            if (pageLoad.equalsIgnoreCase("complete")) {
                break;
            }
        }
    }

    public void isElementVisible(WebElement element, int time) {
        new WebDriverWait(driver, time).until(visibilityOf(element));
    }

}
