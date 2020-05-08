package core.driver.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class DriverManager {
    protected WebDriver driver;

    protected WebDriver createFirefoxDriver() {
        System.setProperty("webdriver.gecko.driver", "webdrivers/win/geckodriver.exe");
        return new FirefoxDriver();
    }

    protected WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--start-maximized");
        System.setProperty("webdriver.chrome.driver", "webdrivers/win/chromedriver.exe");
        return new ChromeDriver(options);
    }
}
