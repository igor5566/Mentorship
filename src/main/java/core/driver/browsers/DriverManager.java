package core.driver.browsers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

@Slf4j
public abstract class DriverManager {

    protected WebDriver driver;

    private String getPathToDriver() {
        String pathToDriver = "";
        String name = "os.name";
        String systemName = System.getProperty(name).toLowerCase();
        if (systemName.startsWith("windows")) {
            pathToDriver = "src/main/resources/webdrivers/win/";
        } else if (systemName.startsWith("linux")) {
            pathToDriver = "src/main/resources/webdrivers/linux/";
        } else if (systemName.startsWith("macos")) {
            pathToDriver = "src/main/resources/webdrivers/mac/";
        } else if(pathToDriver.equals("")) {
            log.error("Unable to detect OS name.");
        }
        return pathToDriver;
    }

    protected WebDriver createFirefoxDriver() {
        System.setProperty("webdriver.gecko.driver", (getPathToDriver() + "geckodriver.exe"));
        return new FirefoxDriver();
    }

    protected WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--start-maximized");
        System.setProperty("webdriver.chrome.driver", (getPathToDriver() + "chromedriver.exe"));
        return new ChromeDriver(options);
    }
}
