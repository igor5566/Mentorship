package com.github.base;

import core.driver.browsers.DriverManagerFactory;
import core.utils.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)
public class BaseTest {

    private DriverManagerFactory driverManagerFactory;
    protected WebDriver driver;

    @BeforeSuite
    public void setUp(ITestContext context) {
        driverManagerFactory = new DriverManagerFactory();
        driver = driverManagerFactory.getWebDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        context.setAttribute("webDriver", driver);
    }

    public void open(String url) {
        driver.get(url);
    }

    @AfterSuite
    public void threadDown() {
        driver.quit();
    }
}
