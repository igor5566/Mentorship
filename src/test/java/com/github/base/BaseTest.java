package com.github.base;

import github.core.driver.browsers.DriverManagerFactory;
import github.core.utils.TestListener;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static github.core.ConfigManager.*;

@Slf4j
@Listeners(TestListener.class)
public class BaseTest {

    private DriverManagerFactory driverManagerFactory;
    protected String uniqueID ;
    protected WebDriver driver;

    @BeforeSuite
    public void setUp(ITestContext context) {
        driverManagerFactory = new DriverManagerFactory();
        driver = driverManagerFactory.getWebDriver();
        driver.manage().timeouts().implicitlyWait(timeWait, TimeUnit.SECONDS);
        context.setAttribute("webDriver", driver);
        setUniqueID();
    }

    public void open(String url) {
        driver.get(url);
    }

    private void setUniqueID() {
        uniqueID = new Date().getTime() + "";
    }

    @AfterSuite
    public void threadDown() {
        if(null != driver) {
            driver.quit();
        }
    }
}
