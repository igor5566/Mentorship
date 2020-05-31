package com.github.pages;

import com.github.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static core.ConfigManager.*;

public class MainPage extends BasePage {

    @FindBy(xpath = "//a[contains(text(), 'Pull requests')]")
    private WebElement pullRequestBtn;

    @FindBy(xpath = "//div[@id='dashboard-repos-container']/div/h2/a")
    private WebElement createNewRepoBtn;

    @FindBy(xpath = "//div[@id='dashboard-repos-container']/div//input")
    private WebElement findRepoField;

    private By repoListItemName = By.xpath("//div[@id='dashboard-repos-container']//ul/li//span[2]");

    private String dashBoardReposContainerXpath = "//div[@id='dashboard-repos-container']";

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

    public CreateRepoPage clickCreateNewRepoBtn() {
        waitUntilPageIsReady(timesWait);
        click(createNewRepoBtn);
        waitUntilPageIsReady(timesWait);
        return new CreateRepoPage(driver);
    }

    public RepoPage typeAndClickRepoByName(String name) {
        type(findRepoField, name);
        click(findElementByText(dashBoardReposContainerXpath, name));
        return new RepoPage(driver);
    }

    public boolean isRepoURLDisappear(String name) {
        type(findRepoField, name);
        try {
            isElementInvisible(repoListItemName, name, timeWait);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
