package com.github.pages;

import com.github.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static core.utils.MavenUtils.*;

public class RepoPage extends BasePage {

    @FindBy(xpath = "//nav[contains(@class, 'js-repo-nav')]/ul/li[9]/a")
    private WebElement settingsBtn;

    public RepoPage(WebDriver driver) {
        super(driver);
    }

    public SettingsPage getSettingsPage() {
        waitUntilPageIsReady(timesWait);
        click(settingsBtn);
        return new SettingsPage(driver);
    }
}
