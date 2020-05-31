package com.github.pages;

import com.github.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewRepoPage extends BasePage {

    @FindBy(xpath = "//div[@class='flex-auto min-width-0 width-fit mr-3']//strong/a")
    private WebElement repoName;

    public NewRepoPage(WebDriver driver) {
        super(driver);
    }

    public boolean checkCreatedRepoName(String name) {
        String actualName = repoName.getText();
        return actualName.equals(name);
    }

}
