package com.github.base;

import static github.core.ConfigManager.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void click(WebElement element) {
        new WebDriverWait(driver, timeWait).until(elementToBeClickable(element)).click();
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

    public void isElementInvisible(By xpath, String text,  int time) {
        new WebDriverWait(driver, time).until(invisibilityOfElementWithText(xpath, text));
    }

    public void scrollPageDown() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

    public WebElement findElementByText(String xpath, String text) {
        String finalXpath = xpath + (String.format("//*[contains(text(),'%s')]", text));
        return driver.findElement(By.xpath(finalXpath));
    }
}
