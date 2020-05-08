package core.driver.browsers;

import core.utils.MavenUtils;
import org.openqa.selenium.WebDriver;

public class DriverManagerFactory extends DriverManager {

    public void getDriverType(String browser) {
        switch (browser) {
            case "firefox-win":
                this.driver = createFirefoxDriverWin();
                break;
            case "firefox-linux":
                this.driver = createFirefoxDriverLinux();
                break;
            case "chrome-linux":
                this.driver = createChromeDriverLinux();
                break;
            default:
                this.driver = createChromeDriverWin();
                break;
        }
    }

    public WebDriver getWebDriver() {
        getDriverType(MavenUtils.getDriverName());
        return this.driver;
    }
}
