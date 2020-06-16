package github.core.driver.browsers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

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
//        options.addArguments("--headless"); //!!!should be enabled for Jenkins
//        options.addArguments("--disable-dev-shm-usage"); //!!!should be enabled for Jenkins
//        options.addArguments("--window-size=1920x1080");// !!!should be enabled for Jenkins
        String path = getPathToDriver() + "chromedriver" + fileType;
        System.setProperty("webdriver.chrome.driver", path);
        return new ChromeDriver(options);
    }

    protected RemoteWebDriver createRemoteDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("81.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);

        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(
                    URI.create("http://localhost:4444/wd/hub").toURL(),
                    capabilities
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(40000, TimeUnit.MILLISECONDS);
        driver.manage().window().maximize();
        return driver;
    }
}
