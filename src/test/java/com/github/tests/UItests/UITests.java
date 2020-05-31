package com.github.tests.UItests;

import com.github.base.BaseTest;
import com.github.pages.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static core.ConfigManager.*;
import static org.assertj.core.api.Assertions.*;

@Slf4j
public class UITests extends BaseTest {

    private final String url = "http://github.com/login";
    private String repoName = "Test";
    private LoginPage loginPage;
    private MainPage mainPage;
    private CreateRepoPage createRepoPage;
    private NewRepoPage newRepoPage;
    private RepoPage repoPage;
    private SettingsPage settingsPage;


    @Test(description = "Verify Home page is opened.")
    public void loginTest() {
        open(url);
        loginPage = new LoginPage(driver);
        mainPage = loginPage.logIn(email, pass);
        assertThat(mainPage.isDashboardVisible()).as("Cannot login into the account.").isTrue();
    }

    @Test(description = "Verify creating new repository.", priority = 1)
    public void creatingRepoTest() {
        createRepoPage = mainPage.clickCreateNewRepoBtn();
        newRepoPage = createRepoPage.createNewRepoByUI(repoName + uniqueID);
        assertThat(newRepoPage.checkCreatedRepoName(repoName + uniqueID)).as("Repo name isn't the same").isTrue();
    }

     @Test(description = "Delete repository.", priority = 2)
     public void deleteRepoTest() {
         open(url);
         repoPage = mainPage.typeAndClickRepoByName(uniqueID);
         settingsPage = repoPage.clickOnSettingsBtn();
         settingsPage.deleteRepoByUI(userID + "/" + repoName + uniqueID);
         assertThat(mainPage.isRepoURLDisappear(uniqueID)).as("Repo is still in the list.").isTrue();
     }
}
