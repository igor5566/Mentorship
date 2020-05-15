package com.github.pages;

import com.github.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static core.utils.MavenUtils.*;

public class MainPage extends BasePage {

    @FindBy(xpath = "//a[contains(text(), 'Pull requests')]")
    private WebElement pullRequestBtn;

    @FindBy(xpath = "//div[@id='dashboard-repos-container']/div/h2/a")
    private WebElement createNewRepoBtn;

    @FindBy(xpath = "//div[@id='dashboard-repos-container']/div//input")
    private WebElement findRepoField;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardVisible() {
        try {
            isElementVisible(pullRequestBtn, timeWait);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public CreateRepoPage getCreateRepoPage() {
        waitUntilPageIsReady(timesWait);
        click(createNewRepoBtn);
        waitUntilPageIsReady(timesWait);
        return new CreateRepoPage(driver);
    }

    public RepoPage getRepoPage(String name) {
        type(findRepoField, name);
        WebElement element = driver.findElement(By.xpath(String.format("//div[@id='dashboard-repos-container']//*[contains(text(),'%s')]", name)));
        click(element);
        return new RepoPage(driver);
    }

}
