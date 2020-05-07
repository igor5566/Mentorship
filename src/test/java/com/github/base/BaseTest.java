package com.github.base;

import core.driver.browsers.DriverManagerFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;

public class BaseTest {

    private DriverManagerFactory driverManagerFactory;
    private WebDriver driver;

    public WebDriver getDriver() {
        driverManagerFactory = new DriverManagerFactory();
        return this.driver = driverManagerFactory.getWebDriver();
    }

    public void open(String url) {
        driver.get(url);
    }

    @AfterSuite
    public void threadDown() {
        driver.quit();
    }
}
