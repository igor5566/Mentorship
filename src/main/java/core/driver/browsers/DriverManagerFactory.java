package core.driver.browsers;

import core.driver.browsers.DriverManager;
import core.utils.MavenUtils;
import org.openqa.selenium.WebDriver;

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
        getDriverType(MavenUtils.getDriverName());
        return this.driver;
    }
}
