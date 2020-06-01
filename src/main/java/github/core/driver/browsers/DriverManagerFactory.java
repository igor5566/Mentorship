package github.core.driver.browsers;

import org.openqa.selenium.WebDriver;
import static github.core.ConfigManager.*;

public class DriverManagerFactory extends DriverManager {

    public void getDriverType(String browser) {
        switch (browser) {
            case "firefox":
                this.driver = createFirefoxDriver();
                break;
            default:
                this.driver = createChromeDriver();
                break;
        }
    }

    public WebDriver getWebDriver() {
        getDriverType(driverName);
        return this.driver;
    }
}
