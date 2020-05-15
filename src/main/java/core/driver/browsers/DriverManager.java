package core.driver.browsers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

@Slf4j
public abstract class DriverManager {

    protected WebDriver driver;
    private String fileType = "";

    private String getPathToDriver() {
        String pathToDriver = "";
        String name = "os.name";
        String systemName = System.getProperty(name).toLowerCase();
        if (systemName.startsWith("windows")) {
            fileType = ".exe";
            pathToDriver = "src/main/resources/webdrivers/win/";
        } else if (systemName.startsWith("linux")) {
            pathToDriver = "src/main/resources/webdrivers/linux/";
        } else if (systemName.startsWith("macos")) {
            pathToDriver = "src/main/resources/webdrivers/mac/";
        } else {
            log.error("Unable to detect OS name.");
        }
        return pathToDriver;
    }

    protected WebDriver createFirefoxDriver() {
        String path = getPathToDriver() + "geckodriver" + fileType;
        System.setProperty("webdriver.gecko.driver", path);
        return new FirefoxDriver();
    }

    protected WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--start-maximized");
        String path = getPathToDriver() + "chromedriver" + fileType;
        System.setProperty("webdriver.chrome.driver", path);
        return new ChromeDriver(options);
    }
}
